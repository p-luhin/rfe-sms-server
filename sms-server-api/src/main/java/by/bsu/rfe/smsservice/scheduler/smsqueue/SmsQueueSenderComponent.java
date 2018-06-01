package by.bsu.rfe.smsservice.scheduler.smsqueue;

import by.bsu.rfe.smsservice.scheduler.SmsServerComponent;
import org.quartz.Job;
import org.springframework.stereotype.Component;

/**
 * Created by pluhin on 1/6/17.
 */
@Component
public class SmsQueueSenderComponent extends SmsServerComponent {

  private static final String SMS_QUEUE_COMPONENT_NAME = "SMS QUEUE COMPONENT";
  private static final String SMS_QUEUE_TRIGGER_NAME = "SMS QUEUE TRIGGER";

  @Override
  public Class<? extends Job> getJobClass() {
    return SmsQueueSenderJob.class;
  }

  @Override
  protected String getCron() {
    return "0 0 0/1 1/1 * ? *";
  }

  @Override
  protected String getJobName() {
    return SMS_QUEUE_COMPONENT_NAME;
  }

  @Override
  protected String getTriggerName() {
    return SMS_QUEUE_TRIGGER_NAME;
  }
}
