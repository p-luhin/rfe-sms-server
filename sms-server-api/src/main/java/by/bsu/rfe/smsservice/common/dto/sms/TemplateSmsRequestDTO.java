package by.bsu.rfe.smsservice.common.dto.sms;

import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TemplateSmsRequestDTO extends BaseSmsRequestDTO {

  @NotNull
  private List<RecipientDTO> recipients;

  @NotNull
  @Size(min = 1)
  private String templateName;

  private Map<String, Map<String, String>> parameters = new HashMap<>();
}
