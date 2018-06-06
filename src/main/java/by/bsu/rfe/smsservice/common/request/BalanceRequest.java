package by.bsu.rfe.smsservice.common.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BalanceRequest {

  private String username;
  private String apiKey;
}
