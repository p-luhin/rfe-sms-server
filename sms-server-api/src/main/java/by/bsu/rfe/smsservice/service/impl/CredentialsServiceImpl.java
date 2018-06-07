package by.bsu.rfe.smsservice.service.impl;

import static java.util.Collections.EMPTY_LIST;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.ShareCredentialsDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.repository.CredentialsRepository;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.BalanceService;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import by.bsu.rfe.smsservice.service.UserService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class CredentialsServiceImpl implements CredentialsService {

  @Autowired
  private CredentialsRepository credentialsRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private ExternalApplicationService externalApplicationService;

  @Autowired
  private CredentialsCache credentialsCache;

  @Autowired
  private Mapper mapper;

  @Autowired
  private BalanceService balanceService;

  @Override
  public List<CredentialsEntity> getAllCredentials() {
    return credentialsRepository.findAll();
  }

  @Override
  public CredentialsEntity getCredentialsForSenderName(String senderName) {
    return credentialsRepository.getCredentialsForSenderName(senderName);
  }

  @Override
  public CredentialsEntity getUserCredentialsForSenderName(String senderName) {
    return credentialsCache.getCredentialsBySenderNameForCurrentUser(senderName);
  }

  @Override
  public void saveCredentials(CredentialsEntity credentialsEntity) {
    balanceService.retrieveBalance(credentialsEntity.getUsername(), credentialsEntity.getApiKey());
    credentialsRepository.saveAndFlush(credentialsEntity);
    credentialsCache.reloadCache();
  }

  @Override
  public List<CredentialsDTO> getUserCredentials(String username) {
    List<CredentialsEntity> credentialsEntities = credentialsCache.getAllUserCredentals(username);

    if (credentialsEntities == null) {
      return EMPTY_LIST;
    }

    List<CredentialsDTO> credentialsDTOs = DozerUtil
        .mapList(mapper, credentialsEntities, CredentialsDTO.class);

    Map<String, Double> balanceByUsername = new HashMap<>();

    for (CredentialsDTO credentialsDTO : credentialsDTOs) {

      if (!balanceByUsername.containsKey(credentialsDTO.getUsername())) {
        Double balance = balanceService
            .retrieveBalance(credentialsDTO.getUsername(), credentialsDTO.getApiKey());
        balanceByUsername.put(credentialsDTO.getUsername(), balance);
      }

      credentialsDTO.setBalance(balanceByUsername.get(credentialsDTO.getUsername()));
    }

    return credentialsDTOs;
  }

  @Override
  public void removeCredentials(Integer id) {
    credentialsRepository.delete(id);
    credentialsCache.reloadCache();
  }

  @Override
  public void shareCredentials(ShareCredentialsDTO shareCredentialsDTO) {
    UserEntity userEntity = userService.findById(shareCredentialsDTO.getUserId());
    CredentialsEntity credentialsEntity = credentialsRepository
        .findOne(shareCredentialsDTO.getCredentialsId());

    credentialsEntity.getUsers().add(userEntity);
    credentialsRepository.saveAndFlush(credentialsEntity);
    credentialsCache.reloadCache();
  }

  @Override
  public CredentialsEntity getCredentialsById(Integer id) {
    return credentialsRepository.findOne(id);
  }
}
