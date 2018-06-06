package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.EmailTemplateDTO;
import java.util.List;

public interface EmailTemplateService {

  List<EmailTemplateDTO> getAllEmailTemplates();

  void saveEmailTemplate(EmailTemplateDTO emailTemplateDTO);

  void removeEmailTemplate(Integer id);

  EmailTemplateDTO getEmailTemplate(String smsType);
}
