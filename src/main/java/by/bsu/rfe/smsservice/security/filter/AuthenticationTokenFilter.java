package by.bsu.rfe.smsservice.security.filter;

import static by.bsu.rfe.smsservice.security.util.SecurityUtil.getAuthToken;

import by.bsu.rfe.smsservice.security.helper.AuthenticationHelper;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Created by pluhin on 3/21/16.
 */
public class AuthenticationTokenFilter extends GenericFilterBean {

  @Autowired
  private List<AuthenticationHelper> authenticationHelpers;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    if (servletRequest instanceof HttpServletRequest) {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

      String token = getAuthToken(httpServletRequest);

      Authentication authentication = null;

      for (AuthenticationHelper helper : authenticationHelpers) {
        authentication = helper.tryWith(token);

        if (authentication != null) {
          break;
        }
      }

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
