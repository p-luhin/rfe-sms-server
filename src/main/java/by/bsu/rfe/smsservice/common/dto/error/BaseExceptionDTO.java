package by.bsu.rfe.smsservice.common.dto.error;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import by.bsu.rfe.smsservice.common.dto.error.FieldErrorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by pluhin on 11/26/16.
 */
@Getter
@NoArgsConstructor
public class BaseExceptionDTO {

  @JsonInclude(NON_NULL)
  private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
  private Integer code;
  private String message;

  public BaseExceptionDTO(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
