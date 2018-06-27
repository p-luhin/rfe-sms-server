package by.bsu.rfe.smsservice.controller;

import static by.bsu.rfe.smsservice.TestConstants.TEST_GROUP_ID;
import static by.bsu.rfe.smsservice.TestConstants.TEST_GROUP_NAME;
import static by.bsu.rfe.smsservice.TestConstants.USERNAME;
import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_APPLICATION;
import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

public class RecipientControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private PersonRepository personRepository;

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_APPLICATION)
  public void createGroupForbiddenForApplication() throws Exception {
    GroupDTO groupDTO = new GroupDTO();

    mockMvc.perform(post("/rest/recipient/group")
        .content(objectMapper.writeValueAsBytes(groupDTO))
        .contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isForbidden());
  }

  @Test
  public void createGroupForbiddenForAnonymous() throws Exception {
    GroupDTO groupDTO = new GroupDTO();

    mockMvc.perform(post("/rest/recipient/group")
        .content(objectMapper.writeValueAsBytes(groupDTO))
        .contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_USER)
  public void createGroupSuccess() throws Exception {
    String groupName = "erwgerger";
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setName(groupName);

    mockMvc.perform(post("/rest/recipient/group")
        .content(objectMapper.writeValueAsBytes(groupDTO))
        .contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isNoContent());

    GroupEntity groupEntity = groupRepository.findAll().stream()
        .filter(groupEntity1 -> groupEntity1.getName().equals(groupName))
        .findFirst()
        .get();
  }

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_USER)
  public void getGroupSuccess() throws Exception {
    mockMvc.perform(get("/rest/recipient/group/{id}", TEST_GROUP_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(TEST_GROUP_NAME))
        .andExpect(jsonPath("$.id").value(TEST_GROUP_ID));
  }

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_APPLICATION)
  public void getGroupForbidden() throws Exception {
    mockMvc.perform(get("/rest/recipient/group"))
        .andExpect(status().isForbidden());
  }

  @Test
  public void getGroupForbiddenForAnonymous() throws Exception {
    mockMvc.perform(get("/rest/recipient/group"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_USER)
  public void removeGroupSuccess() throws Exception {
    mockMvc.perform(delete("/rest/recipient/group/{id}", TEST_GROUP_ID))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_APPLICATION)
  public void removeGroupForbidden() throws Exception {
    mockMvc.perform(delete("/rest/recipient/group/{id}", TEST_GROUP_ID))
        .andExpect(status().isForbidden());
  }

  @Test
  public void removeGroupUnauthorized() throws Exception {
    mockMvc.perform(delete("/rest/recipient/group/{id}", TEST_GROUP_ID))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_USER)
  public void addPersonsSuccess() throws Exception {
    String firstName = "erklgkwler";
    String lastName = "lkernttre";
    String phoneNumber = "+123456789012";
    String email = "email@example.com";

    PersonDTO personDTO = new PersonDTO();
    personDTO.setFirstName(firstName);
    personDTO.setLastName(lastName);
    personDTO.setPhoneNumber(phoneNumber);
    personDTO.setEmail(email);

    mockMvc.perform(post("/rest/recipient/persons")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(objectMapper.writeValueAsBytes(personDTO)))
        .andExpect(status().isNoContent());

    personRepository.findAll()
        .stream()
        .filter(personEntity -> personEntity.getFirstName().equals(firstName))
        .filter(personEntity -> personEntity.getLastName().equals(lastName))
        .findFirst()
        .get();
  }

  @Test
  @WithMockUser(username = USERNAME, authorities = ROLE_APPLICATION)
  public void addPersonsForbidden() throws Exception {
    String firstName = "erklgkwler";
    String lastName = "lkernttre";
    String phoneNumber = "+123456789012";
    String email = "email@example.com";

    PersonDTO personDTO = new PersonDTO();
    personDTO.setFirstName(firstName);
    personDTO.setLastName(lastName);
    personDTO.setPhoneNumber(phoneNumber);
    personDTO.setEmail(email);

    mockMvc.perform(post("/rest/recipient/persons")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(objectMapper.writeValueAsBytes(personDTO)))
        .andExpect(status().isForbidden());
  }

  @Test
  public void addPersonsUnauthorized() throws Exception {
    String firstName = "erklgkwler";
    String lastName = "lkernttre";
    String phoneNumber = "+123456789012";
    String email = "email@example.com";

    PersonDTO personDTO = new PersonDTO();
    personDTO.setFirstName(firstName);
    personDTO.setLastName(lastName);
    personDTO.setPhoneNumber(phoneNumber);
    personDTO.setEmail(email);

    mockMvc.perform(post("/rest/recipient/persons")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(objectMapper.writeValueAsBytes(personDTO)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void getPersons() {

  }

  @Test
  public void getAllPersons() {
  }

  @Test
  public void getPersonsWithGroup() {
  }

  @Test
  public void getPersonsWithoutGroup() {
  }

  @Test
  public void removePerson() {
  }

  @Test
  public void getGroups() {
  }

  @Test
  public void addRecipientToGroup() {
  }

  @Test
  public void getAllRecipients() {
  }
}