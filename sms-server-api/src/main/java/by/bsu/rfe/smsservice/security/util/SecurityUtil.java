package by.bsu.rfe.smsservice.security.util;

import static by.bsu.rfe.smsservice.common.Constants.USER_AUTHORITY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import by.bsu.rfe.smsservice.common.Constants;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

  private static final String APPLICATION_AUTH_TOKEN_KEY = "application_token";

  private static final String ANONYMOUS_USERNAME = "Anonymous";

  public static String getApplicationAuthToken(HttpServletRequest request) {
    return getAuthenticationTokenFromRequest(request);
  }

  private static String getAuthenticationTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader(APPLICATION_AUTH_TOKEN_KEY);
    if (isEmpty(token)) {
      Cookie[] cookies = request.getCookies();
      if (cookies != null && cookies.length > 0) {
        for (Cookie cookie : cookies) {
          if (cookie.getName().equals(APPLICATION_AUTH_TOKEN_KEY)) {
            token = cookie.getValue();
          }
        }
      }
    }
    return token;
  }

  public static String getCurrentUsername() {
    Authentication authentication = getContext().getAuthentication();
    String username = null;
    if (authentication != null) {
      username = authentication.getName();
    }
    return StringUtils.isNotEmpty(username) ? username : ANONYMOUS_USERNAME;
  }

  public static boolean currentUserHasUserRole() {
    return getContext().getAuthentication().getAuthorities().contains(USER_AUTHORITY);
  }
}
