package by.bsu.rfe.smsservice.security.filter;

import static by.bsu.rfe.smsservice.security.util.SecurityUtil.getApplicationAuthToken;

import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.security.common.ApplicationAuthentication;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenFilter extends GenericFilterBean {

  private ExternalApplicationService externalApplicationService;

  @Autowired
  public AuthenticationTokenFilter(
      ExternalApplicationService externalApplicationService) {
    this.externalApplicationService = externalApplicationService;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }

    if (!(servletRequest instanceof HttpServletRequest)) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }

    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

    String applicationToken = getApplicationAuthToken(httpServletRequest);

    if (StringUtils.isEmpty(applicationToken)) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }

    ExternalApplicationEntity applicationEntity = externalApplicationService
        .getByToken(applicationToken);

    if (applicationEntity == null) {
      throw new BadCredentialsException("Provided authentication token is not valid");
    }

    authentication = new ApplicationAuthentication(applicationEntity.getApplicationName(),
        applicationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
