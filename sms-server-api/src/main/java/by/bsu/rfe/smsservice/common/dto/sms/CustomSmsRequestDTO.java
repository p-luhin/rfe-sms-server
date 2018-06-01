package by.bsu.rfe.smsservice.common.dto.sms;

import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomSmsRequestDTO extends BaseSmsRequestDTO {

  @NotNull
  private List<RecipientDTO> recipients;

  @NotNull
  @Size(min = 1)
  private String content;

  @Override
  public boolean isDuplicateEmail() {
    return false;
  }
}
