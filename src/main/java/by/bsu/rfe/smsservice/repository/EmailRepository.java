package by.bsu.rfe.smsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import by.bsu.rfe.smsservice.common.entity.EmailEntity;

/**
 * Created by pluhin on 3/20/16.
 */
public interface EmailRepository extends JpaRepository<EmailEntity, Integer> {

    @Query("FROM EmailEntity WHERE smsTemplate.smsType=?1")
    EmailEntity findBySMSType(String smsType);
}
