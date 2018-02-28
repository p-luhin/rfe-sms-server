package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.util.PageUtil.createPage;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class RecipientServiceImpl implements RecipientService {

  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private Mapper mapper;
  @Autowired
  private List<MobileNumberValidator> mobileNumberValidators;

  @Override
  public void addGroup(GroupDTO groupDTO) {
    GroupEntity groupEntity = mapper.map(groupDTO, GroupEntity.class);

    if (groupEntity.isNew()) {
      groupRepository.saveAndFlush(groupEntity);
      return;
    }

    GroupEntity oldGroupEntity = groupRepository.findOne(groupEntity.getId());
    oldGroupEntity.setName(groupEntity.getName());
    oldGroupEntity.setPersons(groupEntity.getPersons());

    groupRepository.saveAndFlush(oldGroupEntity);
  }

  @Override
  public void removeGroup(Integer groupId) {
    groupRepository.delete(groupId);
  }

  @Override
  public void addPersons(List<PersonEntity> personEntities) {

    for (PersonEntity personEntity : personEntities) {
      for (MobileNumberValidator mobileNumberValidator : mobileNumberValidators) {
        if (!mobileNumberValidator.isValid(personEntity.getPhoneNumber())) {
          throw new IllegalArgumentException(mobileNumberValidator.errorString());
        }
      }
    }

    personRepository.save(personEntities);
  }

  @Override
  public void assignPersonToGroup(Integer personId, Integer groupId) {
    GroupEntity groupEntity = groupRepository.findOne(groupId);
    PersonEntity personEntity = personRepository.findOne(personId);

    groupEntity.getPersons().add(personEntity);
    groupRepository.saveAndFlush(groupEntity);
  }

  @Override
  public List<RecipientDTO> getAllRecpients() {
    List<PersonEntity> persons = personRepository.findAll();
    List<GroupEntity> groups = groupRepository.findAll();
    return mapRecipientListsToRecipientDTOs(persons, groups);
  }

  @Override
  public List<RecipientDTO> getRecipientByQuery(String query) {
    if (StringUtils.isNotEmpty(query)) {
      List<PersonEntity> persons = personRepository.getPersonsByQuery(query);
      List<GroupEntity> groups = groupRepository.getGroupsByQuery(query);
      return mapRecipientListsToRecipientDTOs(persons, groups);
    } else {
      return getAllRecpients();
    }
  }

  @Override
  public void removePerson(Integer personId) {
    personRepository.delete(personId);
  }

  @Override
  public GroupDTO getGroup(Integer groupId) {
    return mapper.map(groupRepository.findOne(groupId), GroupDTO.class);
  }

  @Override
  public PersonEntity getPerson(String[] name) {
    return personRepository.getPersonByFirstNameAndLastName(name[0], name[1]);
  }

  @Override
  public GroupEntity getGroupByName(String groupName) {
    return groupRepository.getGroupsByQuery(groupName).get(0);
  }

  @Override
  public PageResponseDTO<PersonDTO> getPersons(PageRequestDTO pageRequestDTO, String query) {
    Pageable pageable = createPage(pageRequestDTO);

    if (StringUtils.isEmpty(query)) {
      Page<PersonEntity> personEntitiesPage = personRepository.findAll(pageable);
      return new PageResponseDTO<>(
          DozerUtil.mapList(mapper, personEntitiesPage.getContent(), PersonDTO.class),
          personEntitiesPage.getTotalElements());
    }

    Page<PersonEntity> personEntitiesPage = personRepository.findPageByQuery(query, pageable);
    return new PageResponseDTO<>(
        DozerUtil.mapList(mapper, personEntitiesPage.getContent(), PersonDTO.class),
        personEntitiesPage.getTotalElements());
  }

  @Override
  public List<PersonDTO> getAllPersons() {
    return DozerUtil.mapList(mapper, personRepository.findAll(), PersonDTO.class);
  }

  @Override
  public List<PersonDTO> getPersonsWithGroup(Integer groupId) {
    return DozerUtil
        .mapList(mapper, personRepository.getPersonsWithGroup(groupId), PersonDTO.class);
  }

  @Override
  public List<PersonDTO> getPersonsWithoutGroup(Integer groupId) {
    return DozerUtil
        .mapList(mapper, personRepository.getPersonsWithoutGroup(groupId), PersonDTO.class);
  }

  @Override
  public PageResponseDTO<GroupDTO> getGroups(PageRequestDTO pageRequestDTO, String query) {
    Pageable pageable = createPage(pageRequestDTO);
    Page<GroupEntity> entities = null;

    if (StringUtils.isEmpty(query)) {
      entities = groupRepository.findAll(pageable);
    } else {
      entities = groupRepository.findPageByQuery(query, pageable);
    }

    return new PageResponseDTO<>(
        DozerUtil.mapList(mapper, entities.getContent(), GroupDTO.class),
        entities.getTotalElements());
  }

  @Override
  public GroupEntity createGroupFromNumbers(List<String> numbers) {
    GroupEntity groupEntity = new GroupEntity();
    groupEntity.setName("GROUP_" + System.currentTimeMillis());
    groupEntity.setTemporary(true);

    numbers.forEach(number -> {
      PersonEntity personEntity = new PersonEntity();
      personEntity.setFirstName("BULK_TMP");
      personEntity.setLastName("BULK_TMP");
      personEntity.setEmail(number + "@example.com");
      personEntity.setPhoneNumber(number);
      personEntity.setTemporary(true);
      personRepository.saveAndFlush(personEntity);
      groupEntity.getPersons().add(personEntity);
    });

    groupRepository.saveAndFlush(groupEntity);
    return groupEntity;
  }

  @Override
  public List<GroupEntity> findTemporaryGroups() {
    return groupRepository.findTemporaryGroups();
  }

  @Override
  public List<PersonEntity> findTemporaryPersons() {
    return personRepository.findTemporaryPersons();
  }

  private List<RecipientDTO> mapRecipientListsToRecipientDTOs(List<PersonEntity> personEntities,
      List<GroupEntity> groupEntities) {
    List<RecipientDTO> recipients = new ArrayList<>(personEntities.size() + groupEntities.size());

    for (PersonEntity personEntity : personEntities) {
      RecipientDTO recipientDTO = new RecipientDTO();
      recipientDTO.setId(personEntity.getId());
      recipientDTO.setName(personEntity.getFirstName() + " " + personEntity.getLastName());
      recipientDTO.setRecipientType(RecipientType.PERSON);
      recipients.add(recipientDTO);
    }

    for (GroupEntity groupEntity : groupEntities) {
      RecipientDTO recipientDTO = new RecipientDTO();
      recipientDTO.setId(groupEntity.getId());
      recipientDTO.setName(groupEntity.getName());
      recipientDTO.setRecipientType(RecipientType.GROUP);
      recipients.add(recipientDTO);
    }

    return recipients;
  }
}
