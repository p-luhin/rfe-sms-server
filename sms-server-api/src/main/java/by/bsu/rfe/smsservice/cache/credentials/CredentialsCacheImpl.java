package by.bsu.rfe.smsservice.cache.credentials;

import static by.bsu.rfe.smsservice.security.util.SecurityUtil.getCurrentUsername;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.exception.CacheAlreadyLockedException;
import by.bsu.rfe.smsservice.exception.CredentialsNotFoundException;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CredentialsCacheImpl implements CredentialsCache {

  @Autowired
  private CredentialsService credentialsService;

  @Autowired
  private ExternalApplicationService applicationService;

  private volatile boolean locked = false;

  private Map<String, List<CredentialsEntity>> cache = new HashMap<>();

  @PostConstruct
  public void startCache() {
    log.info("INITIALIZING CREDENTIALS CACHE");

    if (locked) {
      throw new CacheAlreadyLockedException();
    }

    lockCache();

    long startTime = System.currentTimeMillis();

    List<CredentialsEntity> userCredentials = credentialsService.getAllCredentials();
    log.info("LOADED {} credentials " + userCredentials.size());

    userCredentials
        .forEach(credentialsEntity -> {
          credentialsEntity.getUsers()
              .forEach(user -> {
                if (!cache.containsKey(user.getUsername())) {
                  cache.put(user.getUsername(), new ArrayList<>());
                }

                cache.get(user.getUsername()).add(credentialsEntity);
              });
        });

    List<ExternalApplicationEntity> applications = applicationService
        .getAllApplicationEntities();
    log.info("LOADED {} applications", applications.size());

    applications
        .forEach(application -> {
          cache.put(application.getApplicationName(),
              Collections.singletonList(application.getDefaultCredentials()));
        });

    log.info("CREDENTIALS CACHE INITIALIZED IN {} ms", System.currentTimeMillis() - startTime);

    unlockCache();
  }

  @Override
  public List<CredentialsEntity> getAllUserCredentals(String username) {
    while (locked) {}
    return cache.get(username);
  }

  @Override
  public Set<String> getSenderNamesForCurrentUser() {
    while (locked) {}
    return getAllUserCredentals(getCurrentUsername())
        .stream()
        .map(CredentialsEntity::getSender)
        .collect(Collectors.toSet());
  }

  @Override
  public CredentialsEntity getCredentialsBySenderNameForCurrentUser(String senderName) {
    while (locked) {}
    return getAllUserCredentals(getCurrentUsername())
        .stream()
        .filter(credentialsEntity -> credentialsEntity.getSender().equals(senderName))
        .findFirst()
        .orElseThrow(CredentialsNotFoundException::new);
  }

  @Override
  public void reloadCache() {
    startCache();
  }

  private void lockCache() {
    locked = true;
    log.info("Successfully acquired cache lock");
  }

  private void unlockCache() {
    locked = false;
    log.info("Cache lock released");
  }
}
