package by.bsu.rfe.smsservice.security.helper;

import org.springframework.security.core.Authentication;

/**
 * Created by pluhin on 7/12/17.
 */
public interface AuthenticationHelper {

  Authentication tryWith(String token);
}
