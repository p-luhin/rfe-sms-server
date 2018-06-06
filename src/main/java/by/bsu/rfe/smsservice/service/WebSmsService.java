package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.response.BalanceResponse;
import by.bsu.rfe.smsservice.common.response.SendSmsResponse;

public interface WebSmsService {

  SendSmsResponse sendSms(Request request);

  BalanceResponse getBalance(Request request);
}
