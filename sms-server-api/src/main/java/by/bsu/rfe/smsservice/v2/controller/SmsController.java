package by.bsu.rfe.smsservice.v2.controller;

import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_APPLICATION;
import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.v2.sms.SmsService;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v2/sms")
@Secured({ROLE_USER, ROLE_APPLICATION})
public class SmsController {

  private final SmsService smsService;

  public SmsController(SmsService smsService) {
    this.smsService = smsService;
  }
}
