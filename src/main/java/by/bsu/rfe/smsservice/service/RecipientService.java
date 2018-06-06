package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
public interface RecipientService {

  void addGroup(GroupDTO groupDTO);

  void removeGroup(Integer groupId);

  void addPersons(List<PersonEntity> personEntities);

  void assignPersonToGroup(Integer personId, Integer groupId);

  List<RecipientDTO> getAllRecpients();

  List<RecipientDTO> getRecipientByQuery(String query);

  void removePerson(Integer personId);

  GroupDTO getGroup(Integer groupId);

  PersonEntity getPerson(String[] name);

  GroupEntity getGroupByName(String groupName);

  PageResponseDTO<PersonDTO> getPersons(PageRequestDTO pageRequestDTO, String query);

  List<PersonDTO> getAllPersons();

  List<PersonDTO> getPersonsWithGroup(Integer groupId);

  List<PersonDTO> getPersonsWithoutGroup(Integer groupId);

  PageResponseDTO<GroupDTO> getGroups(PageRequestDTO pageRequestDTO, String query);

  GroupEntity createGroupFromNumbers(List<String> numbers);

  List<GroupEntity> findTemporaryGroups();

  List<PersonEntity> findTemporaryPersons();
}
