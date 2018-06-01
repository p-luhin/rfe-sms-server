package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.SmsServerPropertyEntity;
import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsServerPropertyRepository extends JpaRepository<SmsServerPropertyEntity, SmsServerProperty> {

    @Query("FROM SmsServerPropertyEntity WHERE propertyGroup = ?1")
    List<SmsServerPropertyEntity> findPropertiesByGroup(
        SmsServerProperty.SmsServerPropertyGroup group);

}
