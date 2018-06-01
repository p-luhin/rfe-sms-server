package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by pluhin on 3/20/16.
 */
@Entity
@Table(name = "email_template")
public class EmailEntity extends CreationDetails {

  private String subject;
  @Lob
  private String content;
  @OneToOne
  @JoinColumn(name = "sms_template")
  private SmsTemplateEntity smsTemplate;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public SmsTemplateEntity getSmsTemplate() {
    return smsTemplate;
  }

  public void setSmsTemplate(SmsTemplateEntity smsTemplate) {
    this.smsTemplate = smsTemplate;
  }
}
