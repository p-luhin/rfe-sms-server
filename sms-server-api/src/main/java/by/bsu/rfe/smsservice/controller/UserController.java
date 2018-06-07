package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.service.UserService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/user", produces = APPLICATION_JSON_UTF8_VALUE)
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/senderNames")
  public ResponseEntity<Set<String>> getSenderNamesForUser() {
    return ok(userService.getSenderNames());
  }
}
