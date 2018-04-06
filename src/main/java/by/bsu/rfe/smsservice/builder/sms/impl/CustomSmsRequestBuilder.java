package by.bsu.rfe.smsservice.builder.sms.impl;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.builder.sms.BaseSmsRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSParam;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomSmsRequestBuilder extends BaseSmsRequestBuilder<CustomSmsRequestDTO> {

  @Autowired
  public CustomSmsRequestBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      List<MobileNumberValidator> mobileNumberValidators,
      RecipientService recipientService) {
    super(parametersCollectorResolver, credentialsService, mobileNumberValidators,
        recipientService);
  }

  @Override
  public Request build(CustomSmsRequestDTO requestDTO) {
    Request request = buildBaseRequest(requestDTO);
    Map<String, String> recipientsByMessages = new HashMap<>();

    requestDTO.getRecipients()
        .forEach(recipient -> {
          Map<String, String> recipientParameters = new HashMap<>();

          parametersCollectorResolver.resolve(recipient.getRecipientType())
              .collectParameters(recipient, recipientParameters);
          recipientsByMessages.putAll(processMessagesAndRecipients(recipientParameters,
              recipient, requestDTO.getContent()));
        });

    String finalMessage = createArrayOfMessages(recipientsByMessages, requestDTO.getSenderName());
    request
        .addParameter(new BasicNameValuePair(WebSMSParam.MESSAGES.getRequestParam(), finalMessage));

    return request;
  }
}
