package by.bsu.rfe.smsservice.common.dto.page;

import lombok.Data;

@Data
public class PageRequestDTO {

  private int offset;
  private int skip;
  private String sortField;
  private String sortDirection;
}
