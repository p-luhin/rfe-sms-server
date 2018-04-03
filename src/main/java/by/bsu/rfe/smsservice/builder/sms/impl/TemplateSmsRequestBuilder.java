package by.bsu.rfe.smsservice.builder.sms.impl;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.builder.sms.BaseSmsRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSParam;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateSmsRequestBuilder extends BaseSmsRequestBuilder<TemplateSmsRequestDTO> {

  private SmsTemplateService smsTemplateService;

  @Autowired
  public TemplateSmsRequestBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      List<MobileNumberValidator> mobileNumberValidators,
      RecipientService recipientService,
      SmsTemplateService smsTemplateService) {
    super(parametersCollectorResolver, credentialsService, mobileNumberValidators,
        recipientService);
    this.smsTemplateService = smsTemplateService;
  }

  @Override
  public Request build(TemplateSmsRequestDTO smsRequestDTO) {
    SmsTemplateEntity smsTemplateEntity = smsTemplateService
        .findSMSTemplate(smsRequestDTO.getTemplateName());
    String message = smsTemplateEntity.getTemplate();

    Request request = buildBaseRequest(smsRequestDTO);
    Map<String, String> recipientsByMessages = new HashMap<>();

    smsRequestDTO.getRecipients()
        .forEach(recipient -> {
          Map<String, String> recipientParameters = Optional
              .ofNullable(smsRequestDTO.getParameters()
                  .get(recipient.getName())).orElseGet(HashMap::new);

          parametersCollectorResolver.resolve(recipient.getRecipientType())
              .collectParameters(recipient, recipientParameters);
          recipientsByMessages.putAll(processMessagesAndRecipients(recipientParameters,
              recipient, message));
        });

    String finalMessage = createArrayOfMessages(recipientsByMessages,
        smsRequestDTO.getSenderName());
    request
        .addParameter(new BasicNameValuePair(WebSMSParam.MESSAGES.getRequestParam(), finalMessage));

    return request;
  }
}
