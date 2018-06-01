package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by pluhin on 3/20/16.
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

  @Query("FROM PersonEntity WHERE firstName=?1 AND lastName=?2")
  PersonEntity getPersonByFirstNameAndLastName(String firstName, String lastName);

  @Query("FROM PersonEntity WHERE firstName LIKE %?1% OR lastName LIKE %?1%")
  List<PersonEntity> getPersonsByQuery(String query);

  @Query("FROM PersonEntity pe JOIN FETCH pe.groups g WHERE :groupId IN g.id")
  List<PersonEntity> getPersonsWithGroup(@Param("groupId") Integer groupId);

  @Query("FROM PersonEntity pe WHERE NOT EXISTS (SELECT grp FROM pe.groups grp WHERE grp.id = :groupId)")
  List<PersonEntity> getPersonsWithoutGroup(@Param("groupId") Integer groupId);

  @Query("SELECT pe FROM PersonEntity pe WHERE pe.firstName LIKE %?1% OR pe.lastName LIKE %?1% OR pe.phoneNumber LIKE %?1%")
  Page<PersonEntity> findPageByQuery(String query, Pageable pageable);

  @Query("FROM PersonEntity WHERE temporary = true")
  List<PersonEntity> findTemporaryPersons();
}
