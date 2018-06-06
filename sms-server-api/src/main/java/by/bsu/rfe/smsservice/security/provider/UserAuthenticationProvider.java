package by.bsu.rfe.smsservice.security.provider;

import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.service.UserService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by pluhin on 3/21/16.
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = (String) authentication.getPrincipal();
    String password = (String) authentication.getCredentials();

    UserEntity userEntity = userService.findByUsername(username);
    if (userEntity != null && userEntity.getPassword().equals(password)) {
      UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(
          username, password, new ArrayList<>());
      SecurityContextHolder.getContext().setAuthentication(authResult);
      return authResult;
    } else {
      throw new BadCredentialsException("Invalid username or password");
    }
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return aClass == UsernamePasswordAuthenticationToken.class;
  }
}
