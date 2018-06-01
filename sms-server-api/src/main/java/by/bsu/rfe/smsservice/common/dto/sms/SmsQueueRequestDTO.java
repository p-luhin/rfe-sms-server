package by.bsu.rfe.smsservice.common.dto.sms;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import java.util.Map;
import lombok.Data;

@Data
public class SmsQueueRequestDTO extends BaseSmsRequestDTO {

  private Integer id;
  private String recipient;
  private RecipientType recipientType;
  private String content;
  private Map<String, String> parameters;
  private String smsType;
  private String initiatedBy;

  @Override
  public boolean isSkipQueue() {
    return true;
  }
}
