package by.bsu.rfe.smsservice.common.dto;

import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.GROUP_NAME_MAX_LENGTH;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.GROUP_NAME_REGEX;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.MIN_LENGTH;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class GroupDTO {

  private Integer id;

  @NotNull
  @Pattern(regexp = GROUP_NAME_REGEX)
  @Size(min = MIN_LENGTH, max = GROUP_NAME_MAX_LENGTH)
  private String name;

  private List<PersonDTO> persons = new ArrayList<>();
}
