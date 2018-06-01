package by.bsu.rfe.smsservice.common.entity;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@Entity
@Table(name = "statistics")
public class StatisticsEntity extends AbstractPersistable<Integer> {

  @Column
  private Boolean error;

  @Column
  private String sender;

  @Column
  private String recipient;

  @Column
  private String text;

  @Lob
  @Column
  private String response;

  @Enumerated(EnumType.STRING)
  @Column(name = "recipient_type")
  private RecipientType recipientType;

  @Column(name = "sent_date")
  private Date sentDate;

  @Column(name = "initiated_by")
  private String initiatedBy;

  @Column
  private String smsType;
}
