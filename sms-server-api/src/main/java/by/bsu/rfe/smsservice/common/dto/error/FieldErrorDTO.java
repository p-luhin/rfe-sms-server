package by.bsu.rfe.smsservice.common.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorDTO {

  private String fieldName;
  private String message;
}
