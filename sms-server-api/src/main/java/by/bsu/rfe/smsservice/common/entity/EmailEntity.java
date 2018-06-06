package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "email_template")
public class EmailEntity extends CreationDetails {

  private String subject;

  @Lob
  private String content;

  @OneToOne
  @JoinColumn(name = "sms_template")
  private SmsTemplateEntity smsTemplate;
}
