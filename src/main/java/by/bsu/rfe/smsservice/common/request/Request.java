package by.bsu.rfe.smsservice.common.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.apache.http.NameValuePair;

@Data
public class Request {

  private String apiEndpoint;
  private List<NameValuePair> parameters;

  public Request() {
    parameters = new ArrayList<>();
  }

  public void addParameter(NameValuePair parameter) {
    parameters.add(parameter);
  }
}
