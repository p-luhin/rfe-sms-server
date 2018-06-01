package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.Constants.PROFILE_LOCAL;

import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.response.BalanceResponse;
import by.bsu.rfe.smsservice.common.response.SendSmsResponse;
import by.bsu.rfe.smsservice.service.WebSmsService;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile(PROFILE_LOCAL)
public class MockWebSmsService implements WebSmsService {

  @Override
  public SendSmsResponse sendSms(Request request) {
    log.info("Simulating send websms request to send sms");
    log.info("request is {}", request);

    return buildSmsResponse();
  }

  @Override
  public BalanceResponse getBalance(Request request) {
    log.info("Simulating send websms request to send sms");
    log.info("request is {}", request);

    return buildBalanceResponse();
  }

  private SendSmsResponse buildSmsResponse() {
    SendSmsResponse sendSmsResponse = new SendSmsResponse();

    sendSmsResponse.setTextResponse("Mock text response");
    sendSmsResponse.setError(System.currentTimeMillis() % 2 == 0);
    sendSmsResponse.setStatus(sendSmsResponse.isError() ? "Error" : "Success");
    sendSmsResponse.setErrorMessage(sendSmsResponse.isError() ? "Some error message" : null);

    return sendSmsResponse;
  }

  private BalanceResponse buildBalanceResponse() {
    BalanceResponse balanceResponse = new BalanceResponse();

    balanceResponse.setStatus("Success");
    balanceResponse.setBalance(ThreadLocalRandom.current().nextDouble(1, 10));

    return balanceResponse;
  }
}
