package by.bsu.rfe.smsservice.scheduler.dataretention;

import by.bsu.rfe.smsservice.scheduler.SmsServerComponent;
import org.quartz.Job;
import org.springframework.stereotype.Component;

@Component
public class DataRetentionComponent extends SmsServerComponent {

  private static final String DATA_RETENTION_COMPONENT_NAME = "DATA RETENTION COMPONENT";
  private static final String DATA_RETENTION_TRIGGER_NAME = "DATA RETENTION TRIGGER";

  @Override
  protected Class<? extends Job> getJobClass() {
    return DataRetentionJob.class;
  }

  @Override
  protected String getCron() {
    return "0 0 0 1/1 * ? *";
  }

  @Override
  protected String getJobName() {
    return DATA_RETENTION_COMPONENT_NAME;
  }

  @Override
  protected String getTriggerName() {
    return DATA_RETENTION_TRIGGER_NAME;
  }
}
