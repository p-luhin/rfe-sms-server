package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.Constants.BULK_SMS_TYPE;
import static by.bsu.rfe.smsservice.common.Constants.CUSTOM_SMS_TYPE;
import static by.bsu.rfe.smsservice.util.MessageUtil.createMessage;

import by.bsu.rfe.smsservice.common.dto.StatisticsDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.response.SendSmsResponse;
import by.bsu.rfe.smsservice.repository.StatisticsRepository;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import by.bsu.rfe.smsservice.util.PageUtil;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

  @Autowired
  private StatisticsRepository statisticsRepository;

  @Autowired
  private SmsTemplateService smsTemplateService;

  @Autowired
  private CredentialsService credentialsService;

  @Autowired
  private Mapper mapper;

  @Override
  public List<StatisticsDTO> getFullStatistics() {
    List<StatisticsEntity> statisticsEntities = statisticsRepository.findAll();
    return DozerUtil.mapList(mapper, statisticsEntities, StatisticsDTO.class);
  }

  public PageResponseDTO getStatisticsPage(PageRequestDTO requestDTO) {
    Pageable pageable = PageUtil.createPage(requestDTO);

    Page<StatisticsEntity> entities = statisticsRepository.findAll(pageable);
    return new PageResponseDTO<>(
        DozerUtil.mapList(mapper, entities.getContent(), StatisticsDTO.class), count());
  }

  @Override
  public Long count() {
    return statisticsRepository.count();
  }

  @Override
  public void saveStatistics(BulkSmsRequestDTO requestDTO, SendSmsResponse response) {
    StatisticsEntity statisticsEntity = new StatisticsEntity();
    statisticsEntity.setInitiatedBy(SecurityUtil.getCurrentUsername());
    statisticsEntity.setSentDate(new Date());
    statisticsEntity.setError(response.isError());
    statisticsEntity.setResponse(response.getTextResponse());
    statisticsEntity.setSender(requestDTO.getSenderName());
    statisticsEntity.setRecipient(requestDTO.getCreatedGroup().getName());
    statisticsEntity.setRecipientType(RecipientType.GROUP);
    statisticsEntity.setText(requestDTO.getMessage());
    statisticsEntity.setSmsType(BULK_SMS_TYPE);
    statisticsRepository.save(statisticsEntity);
  }

  @Override
  public void saveStatistics(CustomSmsRequestDTO requestDTO, SendSmsResponse response) {
    requestDTO.getRecipients().forEach(recipient -> {
      StatisticsEntity statisticsEntity = new StatisticsEntity();
      statisticsEntity.setInitiatedBy(SecurityUtil.getCurrentUsername());
      statisticsEntity.setSentDate(new Date());
      statisticsEntity.setError(response.isError());
      statisticsEntity.setResponse(response.getTextResponse());
      statisticsEntity.setSender(requestDTO.getSenderName());
      statisticsEntity.setRecipient(recipient.getName());
      statisticsEntity.setRecipientType(recipient.getRecipientType());
      statisticsEntity.setText(requestDTO.getContent());
      statisticsEntity.setSmsType(CUSTOM_SMS_TYPE);
      statisticsRepository.save(statisticsEntity);
    });
  }

  @Override
  public void saveStatistics(TemplateSmsRequestDTO requestDTO, SendSmsResponse response) {
    SmsTemplateEntity smsTemplateEntity = smsTemplateService
        .findSMSTemplate(requestDTO.getTemplateName());
    String templateMessage = smsTemplateEntity.getTemplate();

    requestDTO.getRecipients().forEach(recipient -> {
      StatisticsEntity statisticsEntity = new StatisticsEntity();
      statisticsEntity.setInitiatedBy(SecurityUtil.getCurrentUsername());
      statisticsEntity.setSentDate(new Date());
      statisticsEntity.setError(response.isError());
      statisticsEntity.setResponse(response.getTextResponse());
      statisticsEntity.setSender(requestDTO.getSenderName());
      statisticsEntity.setRecipient(recipient.getName());
      statisticsEntity.setRecipientType(recipient.getRecipientType());
      statisticsEntity.setText(
          createMessage(templateMessage, requestDTO.getParameters().get(recipient.getName())));
      statisticsEntity.setSmsType(requestDTO.getTemplateName());
      statisticsRepository.save(statisticsEntity);
    });
  }

  @Override
  public void saveStatistics(SmsQueueRequestDTO requestDTO, SendSmsResponse response) {
    StatisticsEntity statisticsEntity = new StatisticsEntity();
    statisticsEntity.setInitiatedBy(requestDTO.getInitiatedBy());
    statisticsEntity.setSentDate(new Date());
    statisticsEntity.setError(response.isError());
    statisticsEntity.setResponse(response.getTextResponse());
    statisticsEntity.setSender(requestDTO.getSenderName());
    statisticsEntity.setRecipient(requestDTO.getRecipient());
    statisticsEntity.setRecipientType(requestDTO.getRecipientType());
    statisticsEntity.setText(createMessage(requestDTO.getContent(), requestDTO.getParameters()));
    statisticsEntity.setSmsType(requestDTO.getSmsType());
    statisticsRepository.save(statisticsEntity);
  }
}
