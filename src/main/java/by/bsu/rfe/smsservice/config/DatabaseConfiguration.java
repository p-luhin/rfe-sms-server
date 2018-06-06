package by.bsu.rfe.smsservice.config;

import com.mysql.jdbc.Driver;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("by.bsu.rfe.smsservice.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

  @Value("${database.url}")
  private String url;

  @Value("${database.username}")
  private String username;

  @Value("${database.password}")
  private String password;

  @Bean
  public BasicDataSource dataSource() {
    BasicDataSource basicDataSource = new BasicDataSource();

    basicDataSource.setUrl(url);
    basicDataSource.setUsername(username);
    basicDataSource.setPassword(password);
    basicDataSource.setDriverClassName(Driver.class.getName());
    basicDataSource.setValidationQuery("SELECT 1");
    basicDataSource
        .setConnectionProperties("useUnicode=true;characterEncoding=utf8;autoReconnect=true");
    basicDataSource.setTestOnBorrow(true);

    return basicDataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

    factory.setPersistenceUnitName("SMS-Server");
    factory.setDataSource(dataSource());
    factory.setPackagesToScan("by.bsu.rfe.smsservice.common.entity");
    factory.setPersistenceProvider(new HibernatePersistenceProvider());
    factory.setJpaVendorAdapter(adapter);

    return factory;
  }

  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager manager = new JpaTransactionManager();
    manager.setEntityManagerFactory(entityManagerFactory().getObject());
    return manager;
  }

}
