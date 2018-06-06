package by.bsu.rfe.smsservice.builder.parameters;

import by.bsu.rfe.smsservice.builder.parameters.impl.GroupParametersCollector;
import by.bsu.rfe.smsservice.builder.parameters.impl.NumberParametersCollector;
import by.bsu.rfe.smsservice.builder.parameters.impl.PersonParametersCollector;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParametersCollectorResolver {

  @Autowired
  private NumberParametersCollector numberParametersCollector;

  @Autowired
  private PersonParametersCollector personParametersCollector;

  @Autowired
  private GroupParametersCollector groupParametersCollector;

  public ParametersCollector resolve(RecipientType type) {
    if (type == RecipientType.NUMBER) {
      return numberParametersCollector;
    } else if (type == RecipientType.PERSON) {
      return personParametersCollector;
    } else {
      return groupParametersCollector;
    }
  }

}
