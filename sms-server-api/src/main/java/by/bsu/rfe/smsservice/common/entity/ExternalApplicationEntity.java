package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "external_application")
public class ExternalApplicationEntity extends CreationDetails {

  @Column(name = "application_name", nullable = false)
  private String applicationName;

  @Column(name = "authentication", nullable = false)
  private String authenticationToken;

  @OneToOne
  @JoinColumn(name = "default_credentials")
  private CredentialsEntity defaultCredentials;
}
