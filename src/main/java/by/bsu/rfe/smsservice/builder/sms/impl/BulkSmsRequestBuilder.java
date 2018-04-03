package by.bsu.rfe.smsservice.builder.sms.impl;

import static by.bsu.rfe.smsservice.bulk.ExcelUtils.getMessagesFromSheet;
import static by.bsu.rfe.smsservice.bulk.ExcelUtils.getSheetFromFile;
import static by.bsu.rfe.smsservice.common.enums.RecipientType.NUMBER;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.MESSAGES;
import static by.bsu.rfe.smsservice.util.MessageUtil.createMessage;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.builder.sms.BaseSmsRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BulkSmsRequestBuilder extends BaseSmsRequestBuilder<BulkSmsRequestDTO> {

  @Autowired
  public BulkSmsRequestBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      List<MobileNumberValidator> mobileNumberValidators,
      RecipientService recipientService) {
    super(parametersCollectorResolver, credentialsService, mobileNumberValidators,
        recipientService);
  }

  @Override
  public Request build(BulkSmsRequestDTO smsRequestDTO) {
    Request request = buildBaseRequest(smsRequestDTO);

    log.debug("Start processing file with name {}", smsRequestDTO.getFile().getOriginalFilename());
    Sheet sheet = getSheetFromFile(smsRequestDTO.getFile());
    Map<String, String> totalMessages = getMessagesFromSheet(sheet);
    log.debug("Found {} messages", totalMessages.size());

    List<String> numbers = new ArrayList<>(totalMessages.keySet());
    GroupEntity group = recipientService.createGroupFromNumbers(numbers);
    String message = totalMessages.get(numbers.get(0));
    smsRequestDTO.setCreatedGroup(group);
    smsRequestDTO.setMessage(message);

    totalMessages
        .entrySet()
        .forEach(messageAndRecipient -> {
          Map<String, String> parameters = new HashMap<>();
          RecipientDTO recipient = new RecipientDTO(messageAndRecipient.getKey(), NUMBER);
          parametersCollectorResolver.resolve(NUMBER).collectParameters(recipient, parameters);

          String createdMessage = createMessage(message, parameters);
          totalMessages.put(messageAndRecipient.getKey(), createdMessage);
        });

    String finalMessages = createArrayOfMessages(totalMessages, smsRequestDTO.getSenderName());

    request.addParameter(new BasicNameValuePair(MESSAGES.getRequestParam(), finalMessages));
    return request;
  }
}
