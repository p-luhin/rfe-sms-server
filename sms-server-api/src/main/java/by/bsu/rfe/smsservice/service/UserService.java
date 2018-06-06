package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.AuthenticationDTO;
import by.bsu.rfe.smsservice.common.dto.ChangePasswordDTO;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.UserDTO;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pluhin on 3/20/16.
 */
public interface UserService {
    UserDTO authenticate(AuthenticationDTO authenticationDTO);

    UserDTO validateToken(String token);

    UserEntity findByUsername(String username);

    UserEntity findById(Integer id);

    UserEntity getUserByToken(String token);

    void logout(HttpServletRequest req, HttpServletResponse resp, String token);

    Set<String> getSenderNames();

    void addNewCredentials(CredentialsDTO credentialsDTO);

    List<UserDTO> getAllUsers(Integer credentialsId);

    void createUser(String username);

    void removeUser(Integer id);

    void changePassword(ChangePasswordDTO passwordDTO);
}
