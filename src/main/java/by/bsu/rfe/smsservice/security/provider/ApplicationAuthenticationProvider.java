package by.bsu.rfe.smsservice.security.provider;

import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by pluhin on 9/3/16.
 */
public class ApplicationAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private ExternalApplicationService externalApplicationService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (authentication.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return authentication;
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return aClass == UsernamePasswordAuthenticationToken.class;
  }
}
