package by.bsu.rfe.smsservice.common.entity;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * Created by pluhin on 1/4/17.
 */
@Data
@Entity
@Table(name = "sms_queue")
public class SmsQueueEntity extends CreationDetails {

  @Column(name = "recipient")
  private String recipient;

  @Column(name = "recipient_type")
  @Enumerated(EnumType.STRING)
  private RecipientType recipientType;

  @Column(name = "message")
  private String message;

  @OneToOne
  @JoinColumn(name = "credentials_id")
  private CredentialsEntity credentials;

  @Column(name = "duplicate_email")
  private boolean duplicateEmail;

  @Column(name = "parameters_json")
  private String parametersJson;

  @Column(name = "sms_type")
  private String smsType;
}
