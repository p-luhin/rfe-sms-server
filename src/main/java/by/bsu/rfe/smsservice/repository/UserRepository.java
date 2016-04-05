package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 3/21/16.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query("FROM UserEntity WHERE username=?1")
    UserEntity findByUsername(String username);

    @Query("SELECT u FROM UserEntity u JOIN u.tokens t where t.token=?1")
    UserEntity findByToken(String token);
}
