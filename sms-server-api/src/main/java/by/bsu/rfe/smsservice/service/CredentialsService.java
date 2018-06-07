package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.ShareCredentialsDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import java.util.List;

public interface CredentialsService {

  List<CredentialsEntity> getAllCredentials();

  CredentialsEntity getCredentialsForSenderName(String senderName);

  CredentialsEntity getUserCredentialsForSenderName(String senderName);

  void saveCredentials(CredentialsEntity credentialsEntity);

  List<CredentialsDTO> getUserCredentials(String username);

  void removeCredentials(Integer id);

  void shareCredentials(ShareCredentialsDTO shareCredentialsDTO);

  CredentialsEntity getCredentialsById(Integer id);
}
