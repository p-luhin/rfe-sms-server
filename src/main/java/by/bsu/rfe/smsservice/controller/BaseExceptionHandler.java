package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

import by.bsu.rfe.smsservice.common.dto.error.BaseExceptionDTO;
import by.bsu.rfe.smsservice.common.dto.error.FieldErrorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by pluhin on 11/26/16.
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentials(BadCredentialsException e)
      throws JsonProcessingException {
    return status(UNAUTHORIZED)
        .body(new BaseExceptionDTO(UNAUTHORIZED.value(), getMessage(e)));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
    return badRequest().body(new BaseExceptionDTO(BAD_REQUEST.value(), getMessage(e)));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    BindingResult result = ex.getBindingResult();
    BaseExceptionDTO exceptionDTO =
        new BaseExceptionDTO(UNPROCESSABLE_ENTITY.value(), "Validation error");

    result.getFieldErrors()
        .forEach(field -> {
          exceptionDTO.getFieldErrors()
              .add(new FieldErrorDTO(field.getField(), field.getDefaultMessage()));
        });

    return status(UNPROCESSABLE_ENTITY).body(exceptionDTO);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception e) throws JsonProcessingException {
    log.error("Unrecognized exception catch {}", e.getMessage());
    return status(INTERNAL_SERVER_ERROR)
        .body(new BaseExceptionDTO(INTERNAL_SERVER_ERROR.value(), getMessage(e)));
  }

  private String getMessage(Throwable throwable) {
    Throwable root = ExceptionUtils.getRootCause(throwable);
    root = root == null ? throwable : root;
    return root.getMessage();
  }

}
