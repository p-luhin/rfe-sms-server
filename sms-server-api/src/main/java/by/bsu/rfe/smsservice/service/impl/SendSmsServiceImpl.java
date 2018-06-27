package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.dto.SMSResultDTO.fromResponse;
import static by.bsu.rfe.smsservice.util.MuteUtil.isMuted;

import by.bsu.rfe.smsservice.builder.SmsRequestBuilderHolder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.response.SendSmsResponse;
import by.bsu.rfe.smsservice.service.SendEmailService;
import by.bsu.rfe.smsservice.service.SendSmsService;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.service.WebSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendSmsServiceImpl implements SendSmsService {

  private SmsRequestBuilderHolder smsRequestBuilderHolder;
  private WebSmsService webSmsService;
  private SendEmailService sendEmailService;
  private StatisticsService statisticsService;
  private SmsQueueService smsQueueService;

  @Autowired
  public SendSmsServiceImpl(
      SmsRequestBuilderHolder smsRequestBuilderHolder,
      WebSmsService webSmsService, SendEmailService sendEmailService,
      StatisticsService statisticsService,
      SmsQueueService smsQueueService) {
    this.smsRequestBuilderHolder = smsRequestBuilderHolder;
    this.webSmsService = webSmsService;
    this.sendEmailService = sendEmailService;
    this.statisticsService = statisticsService;
    this.smsQueueService = smsQueueService;
  }

  @Override
  public SMSResultDTO sendCustom(CustomSmsRequestDTO requestDTO) {
    if (isMuted()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = smsRequestBuilderHolder.getCustomSmsRequestBuilder().build(requestDTO);
    SendSmsResponse sendSmsResponse = webSmsService.sendSms(request);
    statisticsService.saveStatistics(requestDTO, sendSmsResponse);
    return fromResponse(sendSmsResponse);
  }

  @Override
  public SMSResultDTO sendTemplate(TemplateSmsRequestDTO requestDTO) {
    if (isMuted()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = smsRequestBuilderHolder.getTemplateSmsRequestBuilder().build(requestDTO);
    SendSmsResponse sendSmsResponse = webSmsService.sendSms(request);
    statisticsService.saveStatistics(requestDTO, sendSmsResponse);
    if (requestDTO.isDuplicateEmail()) {
      sendEmailService.sendEmail(requestDTO);
    }
    return fromResponse(sendSmsResponse);
  }

  @Override
  public SMSResultDTO sendBulk(BulkSmsRequestDTO requestDTO) {
    if (isMuted()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = smsRequestBuilderHolder.getBulkSmsRequestBuilder().build(requestDTO);
    SendSmsResponse sendSmsResponse = webSmsService.sendSms(request);
    statisticsService.saveStatistics(requestDTO, sendSmsResponse);
    if (requestDTO.isDuplicateEmail()) {
      sendEmailService.sendEmail(requestDTO);
    }
    return fromResponse(sendSmsResponse);
  }

  @Override
  public SMSResultDTO sendSmsFromQueue(SmsQueueRequestDTO smsQueueRequestDTO) {
    Request request = smsRequestBuilderHolder.getQueueSmsRequestBuilder().build(smsQueueRequestDTO);
    SendSmsResponse sendSmsResponse = webSmsService.sendSms(request);
    statisticsService.saveStatistics(smsQueueRequestDTO, sendSmsResponse);
    if (smsQueueRequestDTO.isDuplicateEmail()) {
      sendEmailService.sendEmail(smsQueueRequestDTO);
    }
    return fromResponse(sendSmsResponse);
  }
}
