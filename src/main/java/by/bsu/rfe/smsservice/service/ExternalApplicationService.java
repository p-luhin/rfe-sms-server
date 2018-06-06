package by.bsu.rfe.smsservice.service;

import java.util.List;
import java.util.Map;

import by.bsu.rfe.smsservice.common.dto.ExternalApplicationDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;

/**
 * Created by pluhin on 9/3/16.
 */
public interface ExternalApplicationService {
    List<ExternalApplicationDTO> getAllExternalApplications();

    void createExternalApplication(ExternalApplicationDTO externalApplicationDTO);

    void removeExternalApplication(Integer id);

    ExternalApplicationEntity getByToken(String token);

    List<ExternalApplicationEntity> getAllApplicationEntities();

    ExternalApplicationEntity getByName(String name);
}
