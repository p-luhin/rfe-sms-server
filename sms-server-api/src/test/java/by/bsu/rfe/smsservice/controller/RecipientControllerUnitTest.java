package by.bsu.rfe.smsservice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.service.RecipientService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class RecipientControllerUnitTest {

  @Mock
  private RecipientService recipientService;

  @InjectMocks
  private RecipientController sut;

  @Test
  public void createGroup() {
    GroupDTO groupDTO = mock(GroupDTO.class);

    ResponseEntity result = sut.createGroup(groupDTO);
    assertEquals(NO_CONTENT, result.getStatusCode());

    verify(recipientService).addGroup(groupDTO);
    verifyNoMoreInteractions(recipientService, groupDTO);
  }

  @Test
  public void getGroup() {
    Integer groupId = 34534234;
    GroupDTO groupDTO = mock(GroupDTO.class);

    when(recipientService.getGroup(any(Integer.class))).thenReturn(groupDTO);

    ResponseEntity<GroupDTO> result = sut.getGroup(groupId);
    assertEquals(OK, result.getStatusCode());
    assertSame(result.getBody(), groupDTO);

    verify(recipientService).getGroup(groupId);
    verifyNoMoreInteractions(recipientService, groupDTO);
  }

  @Test
  public void removeGroup() {
    Integer groupId = 3425324;

    ResponseEntity result = sut.removeGroup(groupId);
    assertEquals(NO_CONTENT, result.getStatusCode());

    verify(recipientService).removeGroup(groupId);
    verifyNoMoreInteractions(recipientService);
  }

  @Test
  public void addPersons() {
    PersonDTO personDTO = mock(PersonDTO.class);

    ResponseEntity result = sut.addPersons(personDTO);
    assertEquals(NO_CONTENT, result.getStatusCode());

    verify(recipientService).addPerson(personDTO);
    verifyNoMoreInteractions(recipientService, personDTO);
  }

  @Test
  public void getPersons() {
    PageRequestDTO pageRequestDTO = mock(PageRequestDTO.class);
    String query = "elrktnkewrle";
    PageResponseDTO<PersonDTO> pageResponseDTO = mock(PageResponseDTO.class);

    when(recipientService.getPersons(any(PageRequestDTO.class), any(String.class)))
        .thenReturn(pageResponseDTO);

    ResponseEntity<PageResponseDTO<PersonDTO>> result = sut.getPersons(pageRequestDTO, query);
    assertEquals(OK, result.getStatusCode());
    assertSame(pageResponseDTO, result.getBody());

    verify(recipientService).getPersons(pageRequestDTO, query);
    verifyNoMoreInteractions(pageRequestDTO, pageResponseDTO, recipientService);
  }

  @Test
  public void getAllPersons() {
    List<PersonDTO> personDTOS = mock(List.class);

    when(recipientService.getAllPersons()).thenReturn(personDTOS);

    ResponseEntity<List<PersonDTO>> result = sut.getAllPersons();
    assertEquals(OK, result.getStatusCode());
    assertSame(personDTOS, result.getBody());

    verify(recipientService).getAllPersons();
    verifyNoMoreInteractions(recipientService, personDTOS);
  }

  @Test
  public void getPersonsWithGroup() {
    Integer groupId = 3425345;
    List<PersonDTO> personDTOS = mock(List.class);

    when(recipientService.getPersonsWithGroup(any(Integer.class))).thenReturn(personDTOS);

    ResponseEntity<List<PersonDTO>> result = sut.getPersonsWithGroup(groupId);
    assertEquals(OK, result.getStatusCode());
    assertSame(personDTOS, result.getBody());

    verify(recipientService).getPersonsWithGroup(groupId);
    verifyNoMoreInteractions(recipientService, personDTOS);
  }

  @Test
  public void getPersonsWithoutGroup() {
    Integer groupId = 2345234;
    List<PersonDTO> personDTOS = mock(List.class);

    when(recipientService.getPersonsWithoutGroup(any(Integer.class))).thenReturn(personDTOS);

    ResponseEntity<List<PersonDTO>> result = sut.getPersonsWithoutGroup(groupId);
    assertEquals(OK, result.getStatusCode());
    assertSame(personDTOS, result.getBody());

    verify(recipientService).getPersonsWithoutGroup(groupId);
    verifyNoMoreInteractions(recipientService, personDTOS);
  }

  @Test
  public void removePerson() {
    Integer personId = 234543;

    ResponseEntity result = sut.removePerson(personId);
    assertEquals(NO_CONTENT, result.getStatusCode());

    verify(recipientService).removePerson(personId);
    verifyNoMoreInteractions(recipientService);
  }

  @Test
  public void getGroups() {
    PageResponseDTO<GroupDTO> pageResponseDTO = mock(PageResponseDTO.class);
    PageRequestDTO pageRequestDTO = mock(PageRequestDTO.class);
    String query = "kj3nrtj23";

    when(recipientService.getGroups(any(PageRequestDTO.class), any(String.class)))
        .thenReturn(pageResponseDTO);


    ResponseEntity<PageResponseDTO<GroupDTO>> result = sut.getGroups(pageRequestDTO, query);
    assertEquals(OK, result.getStatusCode());
    assertSame(pageResponseDTO, result.getBody());

    verify(recipientService).getGroups(pageRequestDTO, query);
    verifyNoMoreInteractions(recipientService, pageRequestDTO, pageResponseDTO);
  }

  @Test
  public void addRecipientToGroup() {
    Integer recipientId = 345234;
    Integer groupId = 423532;

    ResponseEntity result = sut.addRecipientToGroup(recipientId, groupId);
    assertEquals(NO_CONTENT, result.getStatusCode());

    verify(recipientService).assignPersonToGroup(recipientId, groupId);
    verifyNoMoreInteractions(recipientService);
  }

  @Test
  public void getAllRecipients() {
    List<RecipientDTO> recipientDTOS = mock(List.class);
    String query = "l344t234";

    when(recipientService.getRecipientByQuery(any(String.class))).thenReturn(recipientDTOS);

    ResponseEntity<List<RecipientDTO>> result = sut.getAllRecipients(query);
    assertEquals(OK, result.getStatusCode());
    assertSame(recipientDTOS, result.getBody());

    verify(recipientService).getRecipientByQuery(query);
    verifyNoMoreInteractions(recipientService, recipientDTOS);
  }
}