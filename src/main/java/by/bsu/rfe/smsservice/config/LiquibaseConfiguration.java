package by.bsu.rfe.smsservice.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfiguration {

  @Autowired
  private DataSource dataSource;

  @Bean
  public SpringLiquibase springLiquibase() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:liquibase/db.changelog-master.xml");
    liquibase.setDataSource(dataSource);
    return liquibase;
  }
}
