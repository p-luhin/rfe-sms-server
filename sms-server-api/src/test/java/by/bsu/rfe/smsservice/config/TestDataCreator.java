package by.bsu.rfe.smsservice.config;

import static by.bsu.rfe.smsservice.TestConstants.TEST_GROUP_ID;
import static by.bsu.rfe.smsservice.TestConstants.TEST_GROUP_NAME;

import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TestDataCreator {

  @Autowired
  private GroupRepository groupRepository;

  @PostConstruct
  public void init() {
    GroupEntity groupEntity = new GroupEntity();
    groupEntity.setName(TEST_GROUP_NAME);
    groupRepository.saveAndFlush(groupEntity);
    TEST_GROUP_ID = groupEntity.getId();
  }

}
