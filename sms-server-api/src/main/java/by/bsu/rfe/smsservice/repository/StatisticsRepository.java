package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pluhin on 3/20/16.
 */
public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Integer> {
}
