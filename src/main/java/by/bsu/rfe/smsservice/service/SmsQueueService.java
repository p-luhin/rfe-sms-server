package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import java.util.List;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsQueueService {

  void addToQueue(SmsQueueEntity smsQueueEntity);

  List<SmsQueueRequestDTO> getAllSmsFromQueue();

  void removeFromQueue(Integer id);

  SMSResultDTO putToQueue(CustomSmsRequestDTO requestDTO);

  SMSResultDTO putToQueue(BulkSmsRequestDTO requestDTO);

  SMSResultDTO putToQueue(TemplateSmsRequestDTO requestDTO);
}
