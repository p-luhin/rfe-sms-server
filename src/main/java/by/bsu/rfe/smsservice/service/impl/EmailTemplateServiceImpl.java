package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.EmailTemplateDTO;
import by.bsu.rfe.smsservice.common.entity.EmailEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.repository.EmailRepository;
import by.bsu.rfe.smsservice.service.EmailTemplateService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

  @Autowired
  private EmailRepository emailRepository;

  @Autowired
  private SmsTemplateService smsTemplateService;

  @Autowired
  private Mapper mapper;

  @Override
  public List<EmailTemplateDTO> getAllEmailTemplates() {
    return DozerUtil.mapList(mapper, emailRepository.findAll(), EmailTemplateDTO.class);
  }

  @Override
  public void saveEmailTemplate(EmailTemplateDTO emailTemplateDTO) {
    EmailEntity emailEntity = mapper.map(emailTemplateDTO, EmailEntity.class);
    SmsTemplateEntity smsTemplateEntity = smsTemplateService
        .findSMSTemplate(emailTemplateDTO.getSmsType());
    emailEntity.setSmsTemplate(smsTemplateEntity);
    emailRepository.saveAndFlush(emailEntity);
  }

  @Override
  public void removeEmailTemplate(Integer id) {
    emailRepository.delete(id);
  }

  @Override
  public EmailTemplateDTO getEmailTemplate(String smsType) {
    return mapper.map(emailRepository.findBySMSType(smsType), EmailTemplateDTO.class);
  }
}
