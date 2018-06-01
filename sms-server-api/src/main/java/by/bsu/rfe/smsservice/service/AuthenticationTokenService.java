package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.entity.AuthenticationTokenEntity;
import java.util.List;

public interface AuthenticationTokenService {

  List<AuthenticationTokenEntity> getExpiredTokens();

  void removeToken(Integer id);
}
