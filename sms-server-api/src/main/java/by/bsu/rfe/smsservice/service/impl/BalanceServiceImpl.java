package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.builder.RequestBuilderHolder;
import by.bsu.rfe.smsservice.common.request.BalanceRequest;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.response.BalanceResponse;
import by.bsu.rfe.smsservice.service.BalanceService;
import by.bsu.rfe.smsservice.service.WebSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService {

  @Autowired
  private WebSmsService webSmsService;

  @Autowired
  private RequestBuilderHolder requestBuilderHolder;

  @Override
  public Double retrieveBalance(String username, String apiKey) {
    BalanceRequest balanceRequest = new BalanceRequest(username, apiKey);
    Request request = requestBuilderHolder.getBalanceRequestBuilder().build(balanceRequest);
    BalanceResponse response = webSmsService.getBalance(request);
    return response.getBalance();
  }
}
