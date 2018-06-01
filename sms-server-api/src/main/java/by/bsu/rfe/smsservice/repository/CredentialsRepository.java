package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 3/20/16.
 */
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {

    @Query("FROM CredentialsEntity ce JOIN ce.users ue WHERE ue.username=?1")
    CredentialsEntity getDefaultCredentials(String username);

    @Query("FROM CredentialsEntity ce JOIN ce.users ue WHERE ue.username=?1 AND ce.sender=?2")
    CredentialsEntity getUserCredentialsForSenderName(String username, String senderName);

    @Query("FROM CredentialsEntity ce WHERE EXISTS (SELECT usr FROM ce.users usr WHERE usr.username = ?1)")
    List<CredentialsEntity> getAllUserCredentials(String username);

    @Query("FROM CredentialsEntity WHERE sender = ?1")
    CredentialsEntity getCredentialsForSenderName(String senderName);
}
