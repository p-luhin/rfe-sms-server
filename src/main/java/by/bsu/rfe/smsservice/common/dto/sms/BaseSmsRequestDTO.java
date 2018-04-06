package by.bsu.rfe.smsservice.common.dto.sms;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseSmsRequestDTO {

  @Size(min = 1)
  private String senderName;
  private boolean duplicateEmail;
  private boolean skipQueue;
}
