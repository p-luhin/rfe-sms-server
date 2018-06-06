package by.bsu.rfe.smsservice.security.helper;

import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.service.UserService;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by pluhin on 7/12/17.
 */
@Component
public class UserAuthenticationHelper implements AuthenticationHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationHelper.class);

  @Autowired
  private UserService userService;

  @Override
  public Authentication tryWith(String token) {
    UserEntity userEntity = userService.getUserByToken(token);

    if (userEntity == null) {
      return null;
    }

    LOGGER.debug("Authenticating user with token {}", token);
    return new UsernamePasswordAuthenticationToken(
        userEntity.getUsername(),
        userEntity.getPassword(),
        new ArrayList<>());
  }
}
