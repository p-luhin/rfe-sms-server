package by.bsu.rfe.smsservice.builder.sms;

import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.APIKEY;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.SENDER;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.TEST;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.USER;
import static by.bsu.rfe.smsservice.util.MessageUtil.createMessage;
import static java.util.stream.Collectors.toMap;

import by.bsu.rfe.smsservice.builder.WebSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BaseSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSRest;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;

@Slf4j
public abstract class BaseSmsRequestBuilder<T extends BaseSmsRequestDTO> extends
    WebSmsRequestBuilder<T> {

  protected ParametersCollectorResolver parametersCollectorResolver;
  protected CredentialsService credentialsService;
  protected List<MobileNumberValidator> mobileNumberValidators;
  protected RecipientService recipientService;

  public BaseSmsRequestBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      List<MobileNumberValidator> mobileNumberValidators,
      RecipientService recipientService) {
    this.parametersCollectorResolver = parametersCollectorResolver;
    this.credentialsService = credentialsService;
    this.mobileNumberValidators = mobileNumberValidators;
    this.recipientService = recipientService;
  }

  protected Request buildBaseRequest(T requestDTO) {
    Request request = new Request();

    CredentialsEntity credentials;
    if (StringUtils.isEmpty(requestDTO.getSenderName())) {
      credentials = credentialsService.getDefaultCredentialsForCurrentUser();
    } else {
      credentials = credentialsService.getUserCredentialsForSenderName(requestDTO.getSenderName());
    }

    if (credentials == null) {
      throw new NullPointerException("User doesn't allowed to send sms.");
    }

    request.addParameter(new BasicNameValuePair(USER.getRequestParam(), credentials.getUsername()));
    request.addParameter(new BasicNameValuePair(APIKEY.getRequestParam(), credentials.getApiKey()));
    request.addParameter(new BasicNameValuePair(SENDER.getRequestParam(), credentials.getSender()));
    request.addParameter(
        new BasicNameValuePair(TEST.getRequestParam(), System.getProperty("sms.test")));
    request.setApiEndpoint(WebSMSRest.BULK_SEND_MESSAGE.getApiEndpoint());

    return request;
  }

  protected String createArrayOfMessages(Map<String, String> messages, String senderName) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");

    for (Map.Entry<String, String> message : messages.entrySet()) {
      stringBuilder.append("{\"recipient\":\"").append(message.getKey()).append("\",");

      //TODO add bulk sms sending only for bulk types, use single sms sending for custom, template and queue
      stringBuilder.append("\"sender\":\"").append(senderName).append("\",");
      stringBuilder.append("\"message\":\"").append(message.getValue()).append("\"},");
    }

    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  protected Map<String, String> processMessagesAndRecipients(Map<String, String> parameters,
      RecipientDTO recipient, String messageTemplate) {
    List<String> recipients = fetchNumbers(recipient);
    String message = createMessage(messageTemplate, parameters);

    return recipients
        .stream()
        .collect(toMap(
            (rec) -> rec,
            (rec) -> message)
        );
  }

  protected List<String> fetchNumbers(RecipientDTO recipient) {
    List<String> finalRecipients = new ArrayList<>();

    if (recipient.getRecipientType() == RecipientType.NUMBER) {
      String mobileNumber = recipient.getName();

      for (MobileNumberValidator mobileNumberValidator : mobileNumberValidators) {
        mobileNumber = mobileNumberValidator.validate(mobileNumber);
      }

      finalRecipients.add(mobileNumber);
    } else if (recipient.getRecipientType() == RecipientType.GROUP) {
      GroupEntity groupEntity = recipientService.getGroupByName(recipient.getName());
      finalRecipients.add(getAllRecipientsFromGroup(groupEntity));
    } else {
      PersonEntity personEntity = recipientService.getPerson(recipient.getName().split("-"));
      finalRecipients.add(personEntity.getPhoneNumber());
    }
    return finalRecipients;
  }

  protected String getAllRecipientsFromGroup(GroupEntity groupEntity) {
    StringBuilder stringBuilder = new StringBuilder();
    for (PersonEntity person : groupEntity.getPersons()) {
      stringBuilder.append(person.getPhoneNumber());
      stringBuilder.append(",");
    }
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    return stringBuilder.toString();
  }
}
