package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.StatisticsDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.response.SendSmsResponse;
import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
public interface StatisticsService {

  List<StatisticsDTO> getFullStatistics();

  PageResponseDTO getStatisticsPage(PageRequestDTO requestDTO);

  Long count();

  void saveStatistics(BulkSmsRequestDTO requestDTO, SendSmsResponse response);

  void saveStatistics(CustomSmsRequestDTO requestDTO, SendSmsResponse response);

  void saveStatistics(TemplateSmsRequestDTO requestDTO, SendSmsResponse response);

  void saveStatistics(SmsQueueRequestDTO requestDTO, SendSmsResponse response);
}
