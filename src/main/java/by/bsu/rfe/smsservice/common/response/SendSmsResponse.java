package by.bsu.rfe.smsservice.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class SendSmsResponse {

  @JsonProperty("message")
  private String errorMessage;
  private boolean error;
  private List<SmsResponse> messages = new ArrayList<>();
  private String textResponse;
  private String status;

  @Data
  static class SmsResponse {

    @JsonProperty("sms_id")
    private Integer smsId;

    @JsonProperty("custom_id")
    private String customId;
    private Integer parts;
    private Double cost;
    private Double amount;
  }
}
