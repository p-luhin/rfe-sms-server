package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.rfe.smsservice.common.entity.SmsServerPropertyEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsServerPropertyRepository extends JpaRepository<SmsServerPropertyEntity, SmsServerProperty> {

    @Query("FROM SmsServerPropertyEntity WHERE propertyGroup = ?1")
    List<SmsServerPropertyEntity> findPropertiesByGroup(SmsServerProperty.SmsServerPropertyGroup group);

}
