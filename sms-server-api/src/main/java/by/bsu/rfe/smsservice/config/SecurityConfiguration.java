package by.bsu.rfe.smsservice.config;

import by.bsu.rfe.smsservice.security.UnauthorizedEntryPoint;
import by.bsu.rfe.smsservice.security.filter.AuthenticationTokenFilter;
import by.bsu.rfe.smsservice.security.provider.UserAuthenticationProvider;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@ComponentScan("by.bsu.rfe.by.bsu.rfe.smsservice.security.helper")
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Bean
  public UserAuthenticationProvider userAuthenticationProvider() {
    return new UserAuthenticationProvider();
  }

  @Bean
  public AuthenticationTokenFilter authenticationTokenFilter() {
    return new AuthenticationTokenFilter();
  }

  @Bean
  public UnauthorizedEntryPoint unauthorizedEntryPoint() {
    return new UnauthorizedEntryPoint();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManager() {
    ProviderManager providerManager = new ProviderManager(
        Arrays.asList(userAuthenticationProvider()));
    providerManager.setEraseCredentialsAfterAuthentication(false);
    return providerManager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
        .and()
        .addFilterBefore(authenticationTokenFilter(), BasicAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/rest/user/authenticate").permitAll()
        .antMatchers("/rest/**").authenticated();
  }
}
