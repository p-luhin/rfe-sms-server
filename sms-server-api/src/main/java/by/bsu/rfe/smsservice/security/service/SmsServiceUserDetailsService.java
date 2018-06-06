package by.bsu.rfe.smsservice.security.service;

import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.security.common.SmsServerUserDetails;
import by.bsu.rfe.smsservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsServiceUserDetailsService implements UserDetailsService {

  private UserService userService;

  @Autowired
  public SmsServiceUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("Username is: {}", username);
    UserEntity userEntity = userService.findByUsername(username);

    if (userEntity == null) {
      throw new UsernameNotFoundException("Username was not found");
    }

    return new SmsServerUserDetails(userEntity.getUsername(), userEntity.getPassword());
  }
}
