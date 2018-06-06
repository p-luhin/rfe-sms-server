package by.bsu.rfe.smsservice.builder.parameters.impl;

import static by.bsu.rfe.smsservice.common.enums.SmsParams.PHONE_NUMBER;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollector;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NumberParametersCollector extends ParametersCollector {

  @Override
  protected void collectSpecificParameters(RecipientDTO recipient,
      Map<String, String> parameters) {
    parameters.put(PHONE_NUMBER.getKey(), recipient.getName());
  }
}
