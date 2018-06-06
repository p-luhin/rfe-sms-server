package by.bsu.rfe.smsservice.builder.parameters;

import static by.bsu.rfe.smsservice.common.enums.SmsParams.SERVER_URL;

import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import java.util.List;
import java.util.Map;

public abstract class ParametersCollector {

  public final void collectParameters(RecipientDTO recipient,
      Map<String, String> parameters) {
    parameters.put(SERVER_URL.getKey(), System.getProperty("server.url"));
    collectSpecificParameters(recipient, parameters);
  }

  protected abstract void collectSpecificParameters(RecipientDTO recipient,
      Map<String, String> parameters);

}
