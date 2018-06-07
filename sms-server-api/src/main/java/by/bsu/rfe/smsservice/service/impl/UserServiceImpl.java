package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.dto.ChangePasswordDTO;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.repository.UserRepository;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.SendEmailService;
import by.bsu.rfe.smsservice.service.UserService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.codec.digest.DigestUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class UserServiceImpl implements UserService {

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

  @Autowired
  private PasswordEncoder passwordEncoder;

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
  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public UserEntity findById(Integer id) {
    return userRepository.findOne(id);
  }

  @Override
  public Set<String> getSenderNames() {
    return credentialsCache.getSenderNamesForCurrentUser();

  }

  @Override
  public void addNewCredentials(CredentialsDTO credentialsDTO) {
    CredentialsEntity credentialsEntity = mapper.map(credentialsDTO, CredentialsEntity.class);
    UserEntity userEntity = userRepository.findByUsername(SecurityUtil.getCurrentUsername());
    credentialsEntity.getUsers().add(userEntity);
    credentialsService.saveCredentials(credentialsEntity);
    credentialsCache.reloadCache();
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

  @Override
  public UserDTO getAccountInfo() {
    String currentUsername = SecurityUtil.getCurrentUsername();
    return Optional.ofNullable(userRepository.findByUsername(currentUsername))
        .map(user -> mapper.map(user, UserDTO.class))
        .orElseThrow(() -> new UsernameNotFoundException(currentUsername + " was not found"));
  }

  private String generatePassword() {
    char[] buf = new char[8];
    for (int i = 0; i < buf.length; i++) {
      buf[i] = SYMBOLS[RANDOM.nextInt(SYMBOLS.length)];
    }
    return new String(buf);
  }

  private String encryptPassword(String password) {
    String md5Hex = DigestUtils.md5Hex(password);
    return passwordEncoder.encode(md5Hex);
  }
}
