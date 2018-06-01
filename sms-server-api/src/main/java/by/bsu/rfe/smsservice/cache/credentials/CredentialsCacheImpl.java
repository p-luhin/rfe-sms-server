package by.bsu.rfe.smsservice.cache.credentials;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CredentialsCacheImpl implements CredentialsCache {

  @Value("${credentials.cache.enabled:false}")
  private Boolean enableCredentialsCache;

  @Autowired
  private CredentialsService credentialsService;

  @Autowired
  private ExternalApplicationService applicationService;

  private Map<String, List<CredentialsEntity>> credentialsByUsername;
  private Map<String, CredentialsEntity> defaultCredentialsByUsername;

  @PostConstruct
  public void startCache() {
    if (enableCredentialsCache) {
      initCache();
    } else {
      log.info("CREDENTIALS CACHE IS DISABLED");
    }
  }

  private void initCache() {
    log.info("INITIALIZING CREDENTIALS CACHE");
    int count = 0;
    long startTime = System.currentTimeMillis();

    credentialsByUsername = new ConcurrentHashMap<>();
    defaultCredentialsByUsername = new ConcurrentHashMap<>();

    List<CredentialsEntity> userCredentials = credentialsService.getAllCredentials();

    for (CredentialsEntity credentials : userCredentials) {
      log.info("CACHE: LOADED CREDENTIALS WITH SENDER NAME: {}", credentials.getSender());
      Set<UserEntity> usersAllowedToUse = credentials.getUsers();
      for (UserEntity userEntity : usersAllowedToUse) {
        putToAllCache(userEntity, credentials);
        putToDefaultCache(userEntity);
      }
      count++;
    }

    List<ExternalApplicationEntity> allApplications = applicationService
        .getAllApplicationEntities();

    for (ExternalApplicationEntity applicationEntity : allApplications) {
      log.info("CACHE: LOADED APPLICATION CREDENTIALS WITH SENDER NAME: {}",
          applicationEntity.getDefaultCredentials().getSender());
      defaultCredentialsByUsername
          .put(applicationEntity.getApplicationName(), applicationEntity.getDefaultCredentials());
      count++;
    }

    log.info("CREDENTIALS CACHE INITIALIZED IN {} ms", System.currentTimeMillis() - startTime);
    log.info("TOTAL COUNT: {}", count);
  }

  @Override
  public List<CredentialsEntity> getAllUserCredentals(String username) {
    return credentialsByUsername.get(username);
  }

  @Override
  public List<CredentialsEntity> getAllCurrentUserCredentals() {
    String username = SecurityUtil.getCurrentUsername();
    return credentialsByUsername.get(username);
  }

  @Override
  public CredentialsEntity getDefaultCredentialsForUser(String username) {
    return defaultCredentialsByUsername.get(username);
  }

  @Override
  public CredentialsEntity getDefaultCredentialsForCurrentUser() {
    String username = SecurityUtil.getCurrentUsername();
    return defaultCredentialsByUsername.get(username);
  }

  private void putToAllCache(UserEntity userEntity, CredentialsEntity credentialsEntity) {
    if (credentialsByUsername.containsKey(userEntity.getUsername())) {
      credentialsByUsername.get(userEntity.getUsername()).add(credentialsEntity);
    } else {
      List<CredentialsEntity> credentialsEntities = new ArrayList<>();
      credentialsEntities.add(credentialsEntity);
      credentialsByUsername.put(userEntity.getUsername(), credentialsEntities);
    }
  }

  private void putToDefaultCache(UserEntity userEntity) {
    if (userEntity.getDefaultUserCredentials() != null) {
      defaultCredentialsByUsername
          .put(userEntity.getUsername(), userEntity.getDefaultUserCredentials());
    }
  }

  @Override
  public void reloadCache() {
    startCache();
  }

  public Boolean isCacheEnabled() {
    return enableCredentialsCache;
  }

  @Override
  public Set<String> getSenderNamesForCurrentUser() {
    List<CredentialsEntity> credentialsEntities = ListUtils
        .emptyIfNull(getAllCurrentUserCredentals());
    Set<String> senderNames = credentialsEntities.stream()
        .map(credentialsEntity -> credentialsEntity.getSender()).collect(Collectors.toSet());
    return senderNames;
  }

  @Override
  public CredentialsEntity getCredentialsBySenderNameForCurrentUser(String senderName) {
    List<CredentialsEntity> userCredentials = getAllCurrentUserCredentals();

    for (CredentialsEntity credentialsEntity : userCredentials) {
      if (credentialsEntity.getSender().equals(senderName)) {
        return credentialsEntity;
      }
    }

    return null;
  }
}
