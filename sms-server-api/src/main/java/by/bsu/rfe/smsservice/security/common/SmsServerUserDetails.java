package by.bsu.rfe.smsservice.security.common;

import static by.bsu.rfe.smsservice.common.Constants.ACTUATOR_AUTHORITY;
import static by.bsu.rfe.smsservice.common.Constants.USER_AUTHORITY;
import static java.util.Arrays.asList;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
public class SmsServerUserDetails implements UserDetails {

  private final Collection authorities = asList(USER_AUTHORITY, ACTUATOR_AUTHORITY);

  private String username;
  private String password;

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
