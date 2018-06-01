package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.Constants.REGISTER_USER_SMS_TYPE;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.AUTHENTICATION_ENABLED;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.EMAIL_ENABLED;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.PASSWORD;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.SMTP_HOST;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.SMTP_PORT;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.SmsServerPropertyGroup.EMAIL;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.TLS_ENABLED;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.USERNAME;
import static by.bsu.rfe.smsservice.util.MessageUtil.createMessage;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.common.dto.EmailTemplateDTO;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.enums.SmsParams;
import by.bsu.rfe.smsservice.service.EmailTemplateService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.service.SendEmailService;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendEmailServiceImpl implements SendEmailService {

  private static final String EMAIL_PARAMETERS_KEY = "email_address";

  private RecipientService recipientService;
  private EmailTemplateService emailTemplateService;
  private ParametersCollectorResolver parametersCollectorResolver;
  private SmsServerPropertyService smsServerPropertyService;

  @Autowired
  public SendEmailServiceImpl(RecipientService recipientService,
      EmailTemplateService emailTemplateService,
      ParametersCollectorResolver parametersCollectorResolver,
      SmsServerPropertyService smsServerPropertyService) {
    this.recipientService = recipientService;
    this.emailTemplateService = emailTemplateService;
    this.parametersCollectorResolver = parametersCollectorResolver;
    this.smsServerPropertyService = smsServerPropertyService;
  }

  @Async
  @Override
  public void sendEmail(TemplateSmsRequestDTO requestDTO) {
    EmailTemplateDTO emailTemplateDTO = emailTemplateService
        .getEmailTemplate(requestDTO.getTemplateName());

    requestDTO.getRecipients().forEach(recipient -> {
      Map<String, String> parameters = requestDTO.getParameters().get(recipient.getName());
      parametersCollectorResolver.resolve(recipient.getRecipientType())
          .collectParameters(recipient, parameters);

      String addresses = fetchRecipients(recipient, parameters);
      String message = createMessage(emailTemplateDTO.getContent(), parameters);
      sendEmail(addresses, emailTemplateDTO.getSubject(), message);
    });
  }

  @Async
  @Override
  public void sendEmail(SmsQueueRequestDTO requestDTO) {
    RecipientDTO recipient = new RecipientDTO(requestDTO.getRecipient(),
        requestDTO.getRecipientType());

    parametersCollectorResolver.resolve(requestDTO.getRecipientType())
        .collectParameters(recipient, requestDTO.getParameters());

    String addresses = fetchRecipients(recipient, requestDTO.getParameters());
    EmailTemplateDTO emailTemplateDTO = emailTemplateService
        .getEmailTemplate(requestDTO.getSmsType());

    String message = createMessage(emailTemplateDTO.getContent(), requestDTO.getParameters());
    sendEmail(addresses, emailTemplateDTO.getSubject(), message);
  }

  @Override
  public void sendEmail(BulkSmsRequestDTO requestDTO) {
    throw new RuntimeException("Not implemented sending emails for bulk requests");
  }

  @Async
  @Override
  public void sendRegistrationEmail(String username, String password) {
    RecipientDTO recipientTypeEntry = new RecipientDTO(username,
        RecipientType.NUMBER);

    Map<String, String> parameters = new HashMap<>();
    parameters.put(SmsParams.EMAIL.getKey(), username);
    parameters.put(SmsParams.PASSWORD.getKey(), password);
    parameters.put(SmsParams.USERNAME.getKey(), username.split("@")[0]);

    parametersCollectorResolver.resolve(RecipientType.NUMBER)
        .collectParameters(recipientTypeEntry, parameters);

    EmailTemplateDTO emailTemplateDTO = emailTemplateService
        .getEmailTemplate(REGISTER_USER_SMS_TYPE);

    String message = createMessage(emailTemplateDTO.getContent(), parameters);

    sendEmail(username, emailTemplateDTO.getSubject(), message);
  }

  private void sendEmail(String address, String subject, String content) {
    if (!Boolean.valueOf(smsServerPropertyService.findPropertyValue(EMAIL_ENABLED))) {
      log.info("Sending email is disabled via server property");
      return;
    }

    Map<String, String> emailProperties = smsServerPropertyService.findGroupProperties(EMAIL);

    Properties properties = new Properties();
    properties.put("mail.smtp.auth", emailProperties.get(AUTHENTICATION_ENABLED.getDisplayValue()));
    properties.put("mail.smtp.starttls.enable", emailProperties.get(TLS_ENABLED.getDisplayValue()));
    properties.put("mail.smtp.host", emailProperties.get(SMTP_HOST.getDisplayValue()));
    properties.put("mail.smtp.port", emailProperties.get(SMTP_PORT.getDisplayValue()));

    //Temp solution due we don't have certs for email host
    properties.put("mail.smtp.ssl.trust", emailProperties.get(SMTP_HOST.getDisplayValue()));

    Session session = Session.getInstance(properties,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                emailProperties.get(USERNAME.getDisplayValue()),
                emailProperties.get(PASSWORD.getDisplayValue()));
          }
        });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(emailProperties.get(USERNAME.getDisplayValue())));
      message.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse(address));
      message.setSubject(subject);
      message.setContent(content, "text/html; charset=utf-8");

      Transport.send(message);
      log.info("Email to {} address successfully sent", address);
    } catch (MessagingException e) {
      log.error("Could not send email. Exception message is: {}", e.getMessage());
      log.error("{}", e);
      throw new RuntimeException(e);
    }
  }

  private String fetchRecipients(RecipientDTO recipient,
      Map<String, String> parameters) {
    if (recipient.getRecipientType() == RecipientType.NUMBER) {

      for (Map.Entry<String, String> parameter : parameters.entrySet()) {
        if (parameter.getKey().equals(EMAIL_PARAMETERS_KEY)) {
          return parameter.getValue();
        }
      }

      log.error("Could not find email address for recipient {}", recipient.getName());
      log.error("Parameters are: {}", parameters);

      return null;
    } else if (recipient.getRecipientType() == RecipientType.PERSON) {
      PersonEntity personEntity = recipientService.getPerson(recipient.getName().split("-"));
      return personEntity.getEmail();
    }

    GroupEntity groupEntity = recipientService.getGroupByName(recipient.getName());

    StringBuilder emailBuilder = new StringBuilder();

    for (PersonEntity personEntity : groupEntity.getPersons()) {
      emailBuilder.append(personEntity.getEmail());
      emailBuilder.append(",");
    }

    emailBuilder.deleteCharAt(emailBuilder.length() - 1);
    return emailBuilder.toString();
  }
}
