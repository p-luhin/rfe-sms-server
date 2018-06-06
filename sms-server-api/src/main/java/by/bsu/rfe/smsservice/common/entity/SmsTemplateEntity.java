package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sms_template")
public class SmsTemplateEntity extends CreationDetails {

  @Lob
  @Column(name = "template")
  private String template;

  @Column(name = "sms_type", nullable = false)
  private String smsType;

  @Column(name = "enabled")
  private Boolean enabled;
}
