package by.bsu.rfe.smsservice.builder;

import by.bsu.rfe.smsservice.builder.sms.BaseSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.BulkSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.CustomSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.QueueSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.TemplateSmsRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestBuilderHolder {

  private BulkSmsRequestBuilder bulkSmsRequestBuilder;
  private CustomSmsRequestBuilder customSmsRequestBuilder;
  private TemplateSmsRequestBuilder templateSmsRequestBuilder;
  private QueueSmsRequestBuilder queueSmsRequestBuilder;
  private BalanceRequestBuilder balanceRequestBuilder;

  @Autowired
  public RequestBuilderHolder(
      BulkSmsRequestBuilder bulkSmsRequestBuilder,
      CustomSmsRequestBuilder customSmsRequestBuilder,
      TemplateSmsRequestBuilder templateSmsRequestBuilder,
      QueueSmsRequestBuilder queueSmsRequestBuilder,
      BalanceRequestBuilder balanceRequestBuilder) {
    this.bulkSmsRequestBuilder = bulkSmsRequestBuilder;
    this.customSmsRequestBuilder = customSmsRequestBuilder;
    this.templateSmsRequestBuilder = templateSmsRequestBuilder;
    this.queueSmsRequestBuilder = queueSmsRequestBuilder;
    this.balanceRequestBuilder = balanceRequestBuilder;
  }

  public BaseSmsRequestBuilder<SmsQueueRequestDTO> getQueueSmsRequestBuilder() {
    return queueSmsRequestBuilder;
  }

  public BaseSmsRequestBuilder<BulkSmsRequestDTO> getBulkSmsRequestBuilder() {
    return bulkSmsRequestBuilder;
  }

  public BaseSmsRequestBuilder<CustomSmsRequestDTO> getCustomSmsRequestBuilder() {
    return customSmsRequestBuilder;
  }

  public BaseSmsRequestBuilder<TemplateSmsRequestDTO> getTemplateSmsRequestBuilder() {
    return templateSmsRequestBuilder;
  }

  public BalanceRequestBuilder getBalanceRequestBuilder() {
    return balanceRequestBuilder;
  }
}
