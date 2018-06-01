package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.dto.AuthenticationDTO;
import by.bsu.rfe.smsservice.common.dto.ChangePasswordDTO;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.common.entity.AuthenticationTokenEntity;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.repository.UserRepository;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.SendEmailService;
import by.bsu.rfe.smsservice.service.UserService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Mapper mapper;

  @Autowired
  private CredentialsCache credentialsCache;

  @Autowired
  private CredentialsService credentialsService;

  @Autowired
  private SendEmailService sendEmailService;

  private static final char[] SYMBOLS;

  private static final Random RANDOM = new Random();

  static {
    StringBuilder tmp = new StringBuilder();
    for (char ch = '0'; ch <= '9'; ch++) {
      tmp.append(ch);
    }
    for (char ch = 'a'; ch <= 'z'; ch++) {
      tmp.append(ch);
    }
    for (char ch = 'A'; ch <= 'Z'; ch++) {
      tmp.append(ch);
    }
    SYMBOLS = tmp.toString().toCharArray();
  }

  @Override
  @Transactional
  public UserDTO authenticate(AuthenticationDTO authenticationDTO) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        authenticationDTO.getUsername(), authenticationDTO.getPassword());

    authenticationManager.authenticate(authentication);

    UserEntity userEntity = userRepository.findByUsername(authenticationDTO.getUsername());
    String token = generateToken();
    AuthenticationTokenEntity tokenEntity = new AuthenticationTokenEntity();
    tokenEntity.setToken(token);
    tokenEntity.setExpires(DateUtils.addWeeks(new Date(), 2));
    if (userEntity.getTokens() == null) {
      userEntity.setTokens(new ArrayList<>());
    }
    userEntity.getTokens().add(tokenEntity);
    userRepository.saveAndFlush(userEntity);

    UserDTO userDTO = mapper.map(userEntity, UserDTO.class);
    userDTO.setToken(token);
    return userDTO;
  }

  @Override
  public UserDTO validateToken(String token) {
    UserEntity userEntity = userRepository.findByToken(token);
    if (userEntity != null) {
      return mapper.map(userEntity, UserDTO.class);
    }
    return null;
  }

  @Override
  public UserEntity findByUsername(String username) {
    UserEntity userEntity = userRepository.findByUsername(username);
    return userEntity;
  }

  @Override
  public UserEntity findById(Integer id) {
    return userRepository.findOne(id);
  }

  @Override
  public UserEntity getUserByToken(String token) {
    return userRepository.findByToken(token);
  }

  @Override
  public void logout(HttpServletRequest req, HttpServletResponse resp, String token) {
    UserEntity userEntity = getUserByToken(token);

    findAndRemoveToken(token, userEntity.getTokens());
    userRepository.saveAndFlush(userEntity);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(req, resp, auth);
    }
  }

  @Override
  public Set<String> getSenderNames() {
    if (!credentialsCache.isCacheEnabled()) {
      return credentialsService.getUserCredentials(SecurityUtil.getCurrentUsername())
          .stream()
          .map(CredentialsDTO::getSender)
          .collect(Collectors.toSet());
    }
    return credentialsCache.getSenderNamesForCurrentUser();
  }

  @Override
  public void addNewCredentials(CredentialsDTO credentialsDTO) {
    CredentialsEntity credentialsEntity = mapper.map(credentialsDTO, CredentialsEntity.class);
    UserEntity userEntity = userRepository.findByUsername(SecurityUtil.getCurrentUsername());
    credentialsEntity.getUsers().add(userEntity);
    credentialsService.saveCredentials(credentialsEntity);
  }

  @Override
  public List<UserDTO> getAllUsers(Integer credentialsId) {
    List<UserEntity> userEntities;

    if (credentialsId == null) {
      userEntities = userRepository.findAll();
    } else {
      CredentialsEntity credentialsEntity = credentialsService.getCredentialsById(credentialsId);
      List<String> usernames = credentialsEntity.getUsers().stream().map(UserEntity::getUsername)
          .collect(Collectors.toList());
      userEntities = userRepository.findUsersToShareCredentialsWith(usernames);
    }

    return DozerUtil.mapList(mapper, userEntities, UserDTO.class);
  }

  @Override
  public void createUser(String username) {
    String password = generatePassword();

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(username);
    userEntity.setPassword(encryptPassword(password));

    userRepository.saveAndFlush(userEntity);
    sendEmailService.sendRegistrationEmail(username, password);
  }

  @Override
  public void removeUser(Integer id) {
    userRepository.delete(id);
  }

  @Override
  public void changePassword(ChangePasswordDTO passwordDTO) {
    UserEntity userEntity = findByUsername(SecurityUtil.getCurrentUsername());

    if (!userEntity.getPassword().equals(passwordDTO.getOldPassword())) {
      throw new IllegalArgumentException("Old password is invalid");
    }

    userEntity.setPassword(passwordDTO.getNewPassword());
    userRepository.saveAndFlush(userEntity);
  }

  private String generateToken() {
    return UUID.randomUUID().toString();
  }

  private void findAndRemoveToken(String token, List<AuthenticationTokenEntity> tokens) {
    Iterator<AuthenticationTokenEntity> tokenIterator = tokens.iterator();
    while (tokenIterator.hasNext()) {
      AuthenticationTokenEntity tokenEntity = tokenIterator.next();
      if (tokenEntity.getToken().equals(token)) {
        tokenIterator.remove();
      }
    }
  }

  private String generatePassword() {
    char[] buf = new char[8];
    for (int i = 0; i < buf.length; i++) {
      buf[i] = SYMBOLS[RANDOM.nextInt(SYMBOLS.length)];
    }
    return new String(buf);
  }

  private String encryptPassword(String password) {
    return DigestUtils.md5Hex(password);
  }
}
