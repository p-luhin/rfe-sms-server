package by.bsu.rfe.smsservice.controller;

import static by.bsu.rfe.smsservice.common.Constants.ROLE_APPLICATION;
import static by.bsu.rfe.smsservice.common.Constants.ROLE_USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.service.SendSmsService;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@Secured({ROLE_USER})
@RequestMapping(value = "/rest", produces = APPLICATION_JSON_UTF8_VALUE)
public class SMSController {

  @Autowired
  private SendSmsService sendSmsService;

  @Autowired
  private SmsTemplateService smsTemplateService;

  @Autowired
  private SmsQueueService smsQueueService;

  @PostMapping(value = "/sms/send/custom", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SMSResultDTO> sendCustomSms(
      @RequestBody @Valid CustomSmsRequestDTO requestDTO) {
    return ok(sendSmsService.sendCustom(requestDTO));
  }

  @Secured({ROLE_USER, ROLE_APPLICATION})
  @PostMapping(value = "/sms/send/template", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SMSResultDTO> sendTemplateSms(
      @RequestBody @Valid TemplateSmsRequestDTO requestDTO) {
    return ok(sendSmsService.sendTemplate(requestDTO));
  }

  @PostMapping(value = "/sms/send/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<SMSResultDTO> sendBulkSms(@Valid BulkSmsRequestDTO requestDTO) {
    return ok(sendSmsService.sendBulk(requestDTO));
  }

  @GetMapping("/sms/template")
  public ResponseEntity<List<SmsTemplateEntity>> getSMSTemplate() {
    return ok(smsTemplateService.getAllSmsTemplates());
  }

  @GetMapping("/sms/queue")
  public ResponseEntity<List<SmsQueueRequestDTO>> getAllSmsFromQueue() {
    return ok(smsQueueService.getAllSmsFromQueue());
  }

  @DeleteMapping("/sms/queue")
  public ResponseEntity removeSmsFromQueue(@RequestParam("id") Integer id) {
    smsQueueService.removeFromQueue(id);
    return noContent().build();
  }
}
