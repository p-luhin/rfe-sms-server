package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.service.RecipientService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pluhin on 3/5/2016.
 */
@RestController
@RequestMapping(value = "/rest/recipient", produces = APPLICATION_JSON_UTF8_VALUE)
public class RecipientController {

  @Autowired
  private RecipientService recipientService;

  @PostMapping(value = "/group", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity createGroup(@RequestBody GroupDTO groupDTO) {
    recipientService.addGroup(groupDTO);
    return noContent().build();
  }

  @GetMapping("/group/{id}")
  public ResponseEntity<GroupDTO> getGroup(@PathVariable("id") Integer id) {
    return ok(recipientService.getGroup(id));
  }

  @DeleteMapping("/group")
  public ResponseEntity removeGroup(@RequestParam Integer groupId) {
    recipientService.removeGroup(groupId);
    return noContent().build();
  }

  @PostMapping(value = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addPersons(@RequestBody List<PersonEntity> persons) {
    recipientService.addPersons(persons);
    return noContent().build();
  }

  @GetMapping("/persons")
  public ResponseEntity<PageResponseDTO<PersonDTO>> getPersons(PageRequestDTO requestDTO,
      @RequestParam(required = false) String query) {
    return ok(recipientService.getPersons(requestDTO, query));
  }

  @GetMapping("/persons/all")
  public ResponseEntity<List<PersonDTO>> getAllPersons() {
    return ok(recipientService.getAllPersons());
  }

  @GetMapping("/persons/withGroup/{groupId}")
  public ResponseEntity<List<PersonDTO>> getPersonsWithGroup(@PathVariable Integer groupId) {
    return ok(recipientService.getPersonsWithGroup(groupId));
  }

  @GetMapping("/persons/withoutGroup/{groupId}")
  public ResponseEntity<List<PersonDTO>> getPersonsWithoutGroup(@PathVariable Integer groupId) {
    return ok(recipientService.getPersonsWithoutGroup(groupId));
  }

  @DeleteMapping("/persons")
  public ResponseEntity removePerson(@RequestParam Integer personId) {
    recipientService.removePerson(personId);
    return noContent().build();
  }

  @GetMapping("/group")
  public ResponseEntity<PageResponseDTO<GroupDTO>> getGroups(PageRequestDTO pageRequestDTO,
      @RequestParam(required = false) String query) {
    return ok(recipientService.getGroups(pageRequestDTO, query));
  }

  @PostMapping(value = "/add/{recipientId}/toGroup/{groupId}", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity addRecipientToGroup(@PathVariable Integer recipientId,
      @PathVariable Integer groupId) {
    recipientService.assignPersonToGroup(recipientId, groupId);
    return noContent().build();
  }

  @GetMapping("/all")
  public ResponseEntity<List<RecipientDTO>> getAllRecipients(
      @RequestParam(required = false) String query) {
    return ok(recipientService.getRecipientByQuery(query));
  }
}
