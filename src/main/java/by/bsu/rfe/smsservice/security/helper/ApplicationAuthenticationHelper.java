package by.bsu.rfe.smsservice.security.helper;

import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.security.common.ApplicationAuthentication;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by pluhin on 7/12/17.
 */
@Component
public class ApplicationAuthenticationHelper implements AuthenticationHelper {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ApplicationAuthenticationHelper.class);

  @Autowired
  private ExternalApplicationService applicationService;

  @Override
  public Authentication tryWith(String token) {
    ExternalApplicationEntity application = applicationService.getByToken(token);

    if (application == null) {
      return null;
    }

    LOGGER.debug("Authenticating application with token {}", token);
    return new ApplicationAuthentication(application.getApplicationName(),
        application.getAuthenticationToken());
  }
}
