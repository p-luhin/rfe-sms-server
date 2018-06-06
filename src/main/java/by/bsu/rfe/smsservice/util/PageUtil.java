package by.bsu.rfe.smsservice.util;

import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.pagination.ChunkRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

  public static Pageable createPage(PageRequestDTO pageRequestDTO) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortField()));
    return new ChunkRequest(pageRequestDTO.getSkip(), pageRequestDTO.getOffset(), sort);
  }
}
