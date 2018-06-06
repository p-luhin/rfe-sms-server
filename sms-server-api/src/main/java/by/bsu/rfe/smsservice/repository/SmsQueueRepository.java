package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsQueueRepository extends JpaRepository<SmsQueueEntity, Integer> {
}
