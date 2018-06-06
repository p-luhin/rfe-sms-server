package by.bsu.rfe.smsservice.common.entity;

import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sms_server_property")
public class SmsServerPropertyEntity {

  @Id
  @Column(name = "property_key")
  @Enumerated(EnumType.STRING)
  private SmsServerProperty propertyKey;

  @Column(name = "property_group")
  @Enumerated(EnumType.STRING)
  private SmsServerProperty.SmsServerPropertyGroup propertyGroup;

  @Column(name = "value")
  private String value;
}
