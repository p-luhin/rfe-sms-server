package by.bsu.rfe.smsservice.builder;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSRest;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.*;

/**
 * Created by pluhin on 12/27/15.
 */
@Component("sendSMS")
public class SendSMSRequestBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendSMSRequestBuilder.class);

    @Autowired
    private RecipientService recipientService;

    @Autowired
    private CredentialsService credentialsService;

    @Value("${sms.test}")
    private Integer test;

    public Request buildRequest(Map.Entry<String, RecipientType> recipient, Map<String, String> parameters, String smsContent, String requestSenderName) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }

        if (recipient.getValue() != RecipientType.NUMBER) {
            collectAdditionalParameters(recipient, parameters);
        }

        Request request = buildBaseRequest(requestSenderName);

        request.setApiEndpoint(WebSMSRest.SEND_MESSAGE.getApiEndpoint());

        if (recipient.getValue() == RecipientType.NUMBER) {
            request.addParameter(new BasicNameValuePair(RECIPIENTS.getRequestParam(), recipient.getKey()));
        } else if (recipient.getValue() == RecipientType.GROUP) {
            GroupEntity groupEntity = recipientService.getGroup(Integer.valueOf(recipient.getKey()));
            request.addParameter(new BasicNameValuePair(RECIPIENTS.getRequestParam(), getAllRecipientsFromGroup(groupEntity)));
        } else {
            PersonEntity personEntity = recipientService.getPerson(recipient.getKey().split("-"));
            request.addParameter(new BasicNameValuePair(RECIPIENTS.getRequestParam(), personEntity.getPhoneNumber()));
        }

        String message = createMessage(smsContent, parameters, smsContent);

        request.addParameter(new BasicNameValuePair(MESSAGE.getRequestParam(), message));

        return request;
    }

    public Request buildBulkRequest(Map<String, String> messages, String requestSenderName) {
        Request request = buildBaseRequest(requestSenderName);

        request.setApiEndpoint(WebSMSRest.BULK_SEND_MESSAGE.getApiEndpoint());
        String messagesArray = createArrayOfMessages(messages);
        request.addParameter(new BasicNameValuePair(MESSAGES.getRequestParam(), messagesArray));
        return request;
    }

    private Request buildBaseRequest(String requestSenderName) {
        Request request = new Request();

        CredentialsEntity credentials;
        if (StringUtils.isEmpty(requestSenderName)) {
            credentials = credentialsService.getDefaultCredentialsForCurrentUser();
        } else {
            credentials = credentialsService.getCredentialsForSenderName(requestSenderName);
        }

        if (credentials == null) {
            throw new NullPointerException("User doesn't allowed to send sms.");
        }

        request.addParameter(new BasicNameValuePair(USER.getRequestParam(), credentials.getUsername()));
        request.addParameter(new BasicNameValuePair(APIKEY.getRequestParam(), credentials.getApiKey()));
        request.addParameter(new BasicNameValuePair(SENDER.getRequestParam(), credentials.getSender()));
        request.addParameter(new BasicNameValuePair(TEST.getRequestParam(), test.toString()));

        return request;
    }

    private String getAllRecipientsFromGroup(GroupEntity group) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PersonEntity person : group.getPersons()) {
            stringBuilder.append(person.getPhoneNumber());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private void collectAdditionalParameters(Map.Entry<String, RecipientType> recipient, Map<String, String> parameters) {
    }

    private String createMessage(String template, Map<String, String> messageParameters, String originalMessage) {
        String regex = "\\$\\{([^}]+)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);
        String result = template;
        while(matcher.find()) {
            String token = matcher.group();
            String tokenKey = matcher.group(1);
            String replacementValue = null;

            if(messageParameters.containsKey(tokenKey)) {
                replacementValue = messageParameters.get(tokenKey);
            } else {
                LOGGER.error("Not enough parameters. Could not create message.");
                LOGGER.error("Original message: {}", originalMessage);
                LOGGER.error("Parameters: {}", messageParameters);
                throw new IllegalArgumentException("Not enough parameters. Could not create message.");
            }

            result = result.replaceFirst(Pattern.quote(token), replacementValue);
        }

        return result;
    }

    private String createArrayOfMessages(Map<String, String> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for(Map.Entry<String, String> message : messages.entrySet()) {
            stringBuilder.append("{\"recipient\":\"").append(message.getKey()).append("\",");
            stringBuilder.append("\"message\":\"").append(message.getValue()).append("\"},");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
