package by.bsu.rfe.smsservice.common.dto;

import by.bsu.rfe.smsservice.common.response.SendSmsResponse;
import lombok.Data;

/**
 * Created by pluhin on 6/1/16.
 */
@Data
public class SMSResultDTO {

  private boolean error;
  private int count = 0;
  private String lastError;
  private boolean inQueue;

  public static SMSResultDTO fromResponse(SendSmsResponse response) {
    SMSResultDTO resultDTO = new SMSResultDTO();

    resultDTO.setError(response.isError());
    resultDTO.setLastError(response.getErrorMessage());
    resultDTO.setCount(response.getMessages().size());

    return resultDTO;
  }

  public static SMSResultDTO successQueued(int count) {
    SMSResultDTO smsResultDTO = new SMSResultDTO();
    smsResultDTO.setCount(count);
    smsResultDTO.setInQueue(true);
    return smsResultDTO;
  }

  public void incrementTotalCountBy(int count) {
    this.count += count;
  }
}
