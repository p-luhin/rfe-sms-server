package by.bsu.rfe.smsservice.common.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by pluhin on 3/27/16.
 */
@Entity
@Table(name = "authentication_token")
public class AuthenticationTokenEntity extends AbstractPersistable<Integer> {

  private String token;
  private Date expires;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getExpires() {
    return expires;
  }

  public void setExpires(Date expires) {
    this.expires = expires;
  }
}
