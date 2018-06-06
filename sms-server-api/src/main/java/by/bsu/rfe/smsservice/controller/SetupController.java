package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.ChangePasswordDTO;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.EmailTemplateDTO;
import by.bsu.rfe.smsservice.common.dto.ExternalApplicationDTO;
import by.bsu.rfe.smsservice.common.dto.ShareCredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.common.dto.VersionDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.EmailTemplateService;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by pluhin on 9/3/16.
 */
@Controller
@RequestMapping(value = "/rest/setup", produces = APPLICATION_JSON_UTF8_VALUE)
public class SetupController {

  @Autowired
  private UserService userService;

  @Autowired
  private CredentialsService credentialsService;

  @Autowired
  private SmsTemplateService smsTemplateService;

  @Autowired
  private ExternalApplicationService externalApplicationService;

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Autowired
  private SmsServerPropertyService smsServerPropertyService;

  @PostMapping(value = "/credentials", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity addNewCredentials(@RequestBody CredentialsDTO credentialsDTO) {
    userService.addNewCredentials(credentialsDTO);
    return noContent().build();
  }

  @GetMapping("/credentials")
  public ResponseEntity<List<CredentialsDTO>> getUserCredentials() {
    String username = SecurityUtil.getCurrentUsername();
    return ok(credentialsService.getUserCredentials(username));
  }

  @DeleteMapping("/credentials/{id}")
  public ResponseEntity removeCredentials(@PathVariable Integer id) {
    credentialsService.removeCredentials(id);
    return noContent().build();
  }

  @GetMapping("/smsTemplate")
  public ResponseEntity<List<SmsTemplateEntity>> getSmsTemplates() {
    return ok(smsTemplateService.getSmsTemplates());
  }

  @PostMapping(value = "/smsTemplate", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SmsTemplateEntity> addSmsTemplates(
      @RequestBody SmsTemplateEntity smsTemplateEntity) {
    return ok(smsTemplateService.addSMSTemplate(smsTemplateEntity));
  }

  @DeleteMapping("/smsTemplate/{id}")
  public ResponseEntity removeSMSTemplate(@PathVariable Integer id) {
    smsTemplateService.removeSMSTemplate(id);
    return noContent().build();
  }

  @GetMapping("/user")
  public ResponseEntity<List<UserDTO>> getAllUsers(
      @RequestParam(required = false) Integer credentialsId) {
    return ok(userService.getAllUsers(credentialsId));
  }

  @PostMapping(value = "/user", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity createUser(@RequestBody UserDTO userDTO) {
    userService.createUser(userDTO.getUsername());
    return noContent().build();
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity deleteUser(@PathVariable Integer id) {
    userService.removeUser(id);
    return noContent().build();
  }

  @GetMapping("/application")
  public ResponseEntity<List<ExternalApplicationDTO>> getAllApplications() {
    return ok(externalApplicationService.getAllExternalApplications());
  }

  @PostMapping(value = "/application", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity createApplication(@RequestBody ExternalApplicationDTO applicationDTO) {
    externalApplicationService.createExternalApplication(applicationDTO);
    return noContent().build();
  }

  @DeleteMapping("/application/{id}")
  public ResponseEntity deleteApplication(@PathVariable Integer id) {
    externalApplicationService.removeExternalApplication(id);
    return noContent().build();
  }

  @GetMapping("/emailTemplate")
  public ResponseEntity<List<EmailTemplateDTO>> getAllEmailTemplates() {
    return ok(emailTemplateService.getAllEmailTemplates());
  }

  @PostMapping(value = "/emailTemplate", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity createEmailTemplate(@RequestBody EmailTemplateDTO emailTemplateDTO) {
    emailTemplateService.saveEmailTemplate(emailTemplateDTO);
    return noContent().build();
  }

  @DeleteMapping("/emailTemplate/{id}")
  public ResponseEntity deleteEmailTemplate(@PathVariable Integer id) {
    emailTemplateService.removeEmailTemplate(id);
    return noContent().build();
  }

  @GetMapping("/emailTemplate/smsTypes")
  public ResponseEntity<List<String>> getAvailableSmsTypesForEmailTemplate() {
    return ok(smsTemplateService.getAvailableSmsTypesForEmailTemplate());
  }

  @PostMapping(value = "/shareCredentials", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity shareCredentialsBetweenUser(
      @RequestBody ShareCredentialsDTO shareCredentialsDTO) {
    credentialsService.shareCredentials(shareCredentialsDTO);
    return noContent().build();
  }

  @PostMapping(value = "/changePassword", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity changePassword(@RequestBody ChangePasswordDTO passwordDTO) {
    userService.changePassword(passwordDTO);
    return noContent().build();
  }

  @GetMapping("/version")
  public ResponseEntity<VersionDTO> getApplicationVersion() {
    return ok(VersionDTO.loadFromProperties("version.properties"));
  }

  @GetMapping("/properties")
  public ResponseEntity<Map<String, Map<String, String>>> getSmsServerProperties() {
    return ok(smsServerPropertyService.getAllProperties());
  }

  @PostMapping(value = "/properties", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity saveSmsServerProperties(
      @RequestBody Map<String, Map<String, String>> properties) {
    smsServerPropertyService.saveAllProperties(properties);
    return noContent().build();
  }
}
