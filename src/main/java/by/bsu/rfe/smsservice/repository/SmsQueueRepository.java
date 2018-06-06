package by.bsu.rfe.smsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsQueueRepository extends JpaRepository<SmsQueueEntity, Integer> {
}
