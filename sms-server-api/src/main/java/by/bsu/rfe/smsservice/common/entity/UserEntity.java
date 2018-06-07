package by.bsu.rfe.smsservice.common.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity extends CreationDetails {

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "default_credentials")
  private CredentialsEntity defaultUserCredentials;
}
