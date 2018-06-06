package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.AuthenticationDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.UserService;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pluhin on 3/21/16.
 */
@Controller
@RequestMapping(value = "/user", produces = APPLICATION_JSON_UTF8_VALUE)
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping(value = "/authenticate", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<UserDTO> authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
    return ok(userService.authenticate(authenticationDTO));
  }

  @PostMapping(value = "/validateToken", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<UserDTO> validateToken(@RequestBody String token) {
    return ok(userService.validateToken(token));
  }

  @PostMapping("/logout")
  public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
    String token = SecurityUtil.getUserAuthToken(request);
    userService.logout(request, response, token);
    return noContent().build();
  }

  @GetMapping("/senderNames")
  public ResponseEntity<Set<String>> getSenderNamesForUser() {
    return ok(userService.getSenderNames());
  }
}
