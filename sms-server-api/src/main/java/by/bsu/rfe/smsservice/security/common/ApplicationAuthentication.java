package by.bsu.rfe.smsservice.security.common;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by pluhin on 7/12/17.
 */
public class ApplicationAuthentication implements Authentication {

  private String applicationName;

  private String token;

  private boolean authenticated;

  public ApplicationAuthentication(String applicationName, String token) {
    this.applicationName = applicationName;
    this.token = token;
    this.authenticated = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return applicationName;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return applicationName;
  }
}
