package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;

public interface SendEmailService {

  void sendEmail(TemplateSmsRequestDTO requestDTO);

  void sendEmail(SmsQueueRequestDTO requestDTO);

  void sendEmail(BulkSmsRequestDTO requestDTO);

  void sendRegistrationEmail(String username, String password);
}
