package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.StatisticsDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.service.StatisticsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pluhin on 12/27/15.
 */
@Controller
@RequestMapping(value = "/statistics", produces = APPLICATION_JSON_UTF8_VALUE)
public class StatisticsController {

  @Autowired
  private StatisticsService statisticsService;

  @GetMapping
  public ResponseEntity<List<StatisticsDTO>> getFullStatistics() {
    return ok(statisticsService.getFullStatistics());
  }

  @GetMapping("/page")
  public ResponseEntity<PageResponseDTO> getStatisticsPage(PageRequestDTO requestDTO) {
    return ok(statisticsService.getStatisticsPage(requestDTO));
  }

  @GetMapping("/count")
  public ResponseEntity<Long> countStatistics() {
    return ok(statisticsService.count());
  }
}
