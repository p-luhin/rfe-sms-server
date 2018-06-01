package by.bsu.rfe.smsservice.repository;

import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by pluhin on 3/20/16.
 */
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {
    @Query("FROM GroupEntity WHERE name LIKE %?1%")
    List<GroupEntity> getGroupsByQuery(String query);

    @Query("SELECT ge FROM GroupEntity ge WHERE ge.name LIKE %?1%")
    Page<GroupEntity> findPageByQuery(String query, Pageable pageable);

    @Query("FROM GroupEntity WHERE temporary = true")
    List<GroupEntity> findTemporaryGroups();
}
