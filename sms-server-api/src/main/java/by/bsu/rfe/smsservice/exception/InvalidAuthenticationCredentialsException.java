package by.bsu.rfe.smsservice.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(UNAUTHORIZED)
public class InvalidAuthenticationCredentialsException extends RuntimeException {

  public InvalidAuthenticationCredentialsException(String message) {
    super(message);
  }
}
