package by.bsu.rfe.smsservice.common.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@Entity
@Table(name = "authentication_token")
public class AuthenticationTokenEntity extends AbstractPersistable<Integer> {

  private String token;
  private Date expires;
}
