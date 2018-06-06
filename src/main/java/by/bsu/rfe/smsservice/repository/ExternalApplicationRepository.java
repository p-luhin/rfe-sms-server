package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

/**
 * Created by pluhin on 9/3/16.
 */
public interface ExternalApplicationRepository extends JpaRepository<ExternalApplicationEntity, Integer> {

    @Query("FROM ExternalApplicationEntity WHERE authenticationToken=?1")
    ExternalApplicationEntity getByToken(String token);

    @Query("FROM ExternalApplicationEntity WHERE applicationName=?1")
    ExternalApplicationEntity getByName(String name);
}
