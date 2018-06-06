package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.AuthenticationTokenEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthenticationTokenRepository extends
    JpaRepository<AuthenticationTokenEntity, Integer> {

  @Query("FROM AuthenticationTokenEntity WHERE expires <= CURRENT_DATE")
  List<AuthenticationTokenEntity> getExpiredTokens();

}
