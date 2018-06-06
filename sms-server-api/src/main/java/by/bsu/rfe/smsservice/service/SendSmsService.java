package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;

public interface SendSmsService {

  SMSResultDTO sendCustom(CustomSmsRequestDTO requestDTO);

  SMSResultDTO sendTemplate(TemplateSmsRequestDTO requestDTO);

  SMSResultDTO sendBulk(BulkSmsRequestDTO requestDTO);

  SMSResultDTO sendSmsFromQueue(SmsQueueRequestDTO smsQueueRequestDTO);
}
