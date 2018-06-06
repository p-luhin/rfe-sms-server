package by.bsu.rfe.smsservice.security;

import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Created by pluhin on 1/13/16.
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnauthorizedEntryPoint.class);

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    LOGGER.info("ACCESS REJECTED to user " + SecurityUtil.getCurrentUsername() + " to URL "
        + httpServletRequest.getRequestURI());
    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
