package by.bsu.rfe.smsservice.service;

import java.util.List;

import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;

/**
 * Created by pluhin on 3/20/16.
 */
public interface CredentialsService {
    List<CredentialsEntity> getAllCredentials();
    CredentialsEntity getDefaultCredentialsForCurrentUser();
    CredentialsEntity getCredentialsForSenderName(String senderName);
    void addNewCredentials(CredentialsEntity credentialsEntity);
    List<CredentialsDTO> getUserCredentials(String username);
    void removeCredentials(Integer id);
}
