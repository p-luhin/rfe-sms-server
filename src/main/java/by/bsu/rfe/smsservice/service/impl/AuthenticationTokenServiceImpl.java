package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.entity.AuthenticationTokenEntity;
import by.bsu.rfe.smsservice.repository.AuthenticationTokenRepository;
import by.bsu.rfe.smsservice.service.AuthenticationTokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {

  @Autowired
  private AuthenticationTokenRepository tokenRepository;

  @Override
  public List<AuthenticationTokenEntity> getExpiredTokens() {
    return tokenRepository.getExpiredTokens();
  }

  @Override
  public void removeToken(Integer id) {
    tokenRepository.delete(id);
  }
}
