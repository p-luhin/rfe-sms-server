package by.bsu.rfe.smsservice.common.dto.sms;

import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BulkSmsRequestDTO extends BaseSmsRequestDTO {

  @NotNull
  private MultipartFile file;
  private GroupEntity createdGroup;
  private String message;

  @Override
  public boolean isDuplicateEmail() {
    return false;
  }
}
