package by.bsu.rfe.smsservice.scheduler.smsqueue;

import static by.bsu.rfe.smsservice.util.MuteUtil.isMuted;
import static java.util.stream.Collectors.toList;

import by.bsu.rfe.smsservice.common.SpringContextHolder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.service.SendSmsService;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pluhin on 1/6/17.
 */
public class SmsQueueSenderJob implements Job {

  private static final Logger LOGGER = LoggerFactory.getLogger(SmsQueueSenderJob.class);

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    SmsQueueService smsQueueService = SpringContextHolder.getBean(SmsQueueService.class);
    SendSmsService sendSmsService = SpringContextHolder.getBean(SendSmsService.class);
    LOGGER.info("Executing sms queue sender job");

    if (!isMuted()) {

      List<SmsQueueRequestDTO> smsFromQueue = smsQueueService.getAllSmsFromQueue();

      List<SMSResultDTO> results = smsFromQueue
          .stream()
          .map(sendSmsService::sendSmsFromQueue)
          .collect(toList());

      SMSResultDTO totalSmsResult = new SMSResultDTO();
      results
          .stream()
          .forEach(result -> {
            totalSmsResult.incrementTotalCountBy(result.getCount());
            if (result.isError()) {
              totalSmsResult.setError(true);
              totalSmsResult.setLastError(result.getLastError());
            }
          });

      if (totalSmsResult.getCount() == 0) {
        LOGGER.info("There is no sms's in queue");
        return;
      }

      if (!totalSmsResult.isError()) {
        LOGGER.info("All sms's successfully sent");
        LOGGER.info("Total count: {}", totalSmsResult.getCount());
      } else {
        LOGGER.error("There is errors while sending sms's from queue");
        LOGGER.error("Last error is: ");
        LOGGER.error("{}", totalSmsResult.getLastError());
        LOGGER.info("Total count: {}", totalSmsResult.getCount());
      }

      List<Integer> smsQueueIds = smsFromQueue
          .stream()
          .map(SmsQueueRequestDTO::getId)
          .collect(toList());

      smsQueueIds.forEach(smsQueueService::removeFromQueue);

    } else {
      LOGGER.info("Mute mode is enabled.");
    }
  }
}
