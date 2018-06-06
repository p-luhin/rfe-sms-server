package by.bsu.rfe.smsservice.common.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 * Created by PLugin on 14.11.2015.
 */
@Data
@Entity
@Table(name = "credentials")
public class CredentialsEntity extends CreationDetails {

  @Column(name = "username")
  private String username;

  @Column(name = "sender")
  private String sender;

  @Column(name = "api_key")
  private String apiKey;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_credentials",
      joinColumns = @JoinColumn(name = "credentials_id"),
      inverseJoinColumns = @JoinColumn(name = "users_id")
  )
  private Set<UserEntity> users = new HashSet<>();
}
