package by.bsu.rfe.smsservice.config;

import by.bsu.rfe.smsservice.common.SpringContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
    "by.bsu.rfe.smsservice.service.impl",
    "by.bsu.rfe.smsservice.builder",
    "by.bsu.rfe.smsservice.validator"
})
public class ApplicationConfiguration {

  @Bean
  public Mapper dozerMapper() {
    return new DozerBeanMapper();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public SpringContextHolder contextHolder() {
    return new SpringContextHolder();
  }
}
