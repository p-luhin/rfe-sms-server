package by.bsu.rfe.smsservice.builder.balance;

import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.APIKEY;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.USER;
import static by.bsu.rfe.smsservice.common.websms.WebSMSRest.BALANCE;

import by.bsu.rfe.smsservice.builder.WebSmsRequestBuilder;
import by.bsu.rfe.smsservice.common.request.BalanceRequest;
import by.bsu.rfe.smsservice.common.request.Request;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

@Component
public class BalanceRequestBuilder extends WebSmsRequestBuilder<BalanceRequest> {

  @Override
  public Request build(BalanceRequest balanceRequest) {
    Request request = new Request();
    request.setApiEndpoint(BALANCE.getApiEndpoint());
    request.addParameter(
        new BasicNameValuePair(USER.getRequestParam(), balanceRequest.getUsername()));
    request
        .addParameter(new BasicNameValuePair(APIKEY.getRequestParam(), balanceRequest.getApiKey()));

    return request;
  }
}
