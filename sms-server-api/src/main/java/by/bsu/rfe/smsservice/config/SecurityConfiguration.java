package by.bsu.rfe.smsservice.config;

import by.bsu.rfe.smsservice.security.UnauthorizedEntryPoint;
import by.bsu.rfe.smsservice.security.filter.AuthenticationTokenFilter;
import by.bsu.rfe.smsservice.security.handler.ErrorAuthenticationHandler;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private ExternalApplicationService applicationService;

  @Autowired
  private ErrorAuthenticationHandler errorAuthenticationHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(4);
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());

    return provider;
  }

  @Bean
  public AuthenticationTokenFilter authenticationTokenFilter() {
    return new AuthenticationTokenFilter(applicationService);
  }

  @Bean
  public UnauthorizedEntryPoint unauthorizedEntryPoint() {
    return new UnauthorizedEntryPoint();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
        .and()
        .formLogin()
        .loginProcessingUrl("/rest/user/authenticate")
        .usernameParameter("j_username")
        .passwordParameter("j_password")
        .failureHandler(errorAuthenticationHandler)
        .permitAll()
        .and()
        .addFilterAfter(authenticationTokenFilter(), SessionManagementFilter.class)
        .authorizeRequests()
        .antMatchers("/rest/user/authenticate").permitAll()
        .antMatchers("/rest/**").authenticated()
        .antMatchers("/**").permitAll();
  }
}
