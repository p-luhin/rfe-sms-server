package by.bsu.rfe.smsservice.config;

import by.bsu.rfe.smsservice.common.SpringContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({
    "by.bsu.rfe.by.bsu.rfe.smsservice.service.impl",
    "by.bsu.rfe.by.bsu.rfe.smsservice.builder",
    "by.bsu.rfe.by.bsu.rfe.smsservice.validator",
    "by.bsu.rfe.by.bsu.rfe.smsservice.builder.parameters"
})
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public SpringContextHolder contextHolder() {
    return new SpringContextHolder();
  }
}
