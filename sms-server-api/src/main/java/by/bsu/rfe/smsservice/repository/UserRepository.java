package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  @Query("FROM UserEntity WHERE username=?1")
  UserEntity findByUsername(String username);

  @Query("FROM UserEntity WHERE username NOT IN ?1")
  List<UserEntity> findUsersToShareCredentialsWith(List<String> usersWithThatCredentials);
}
