package by.bsu.rfe.smsservice.config;

import by.bsu.rfe.smsservice.common.util.JSR310DateTimeSerializer;
import by.bsu.rfe.smsservice.common.util.JSR310LocalDateDeserializer;
import by.bsu.rfe.smsservice.service.WebSmsService;
import by.bsu.rfe.smsservice.service.impl.MockWebSmsService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan(basePackages = "by.bsu.rfe.smsservice")
@SpringBootTest
@EnableAutoConfiguration
public class ApplicationTestContext extends WebMvcConfigurerAdapter {

  @Bean
  public WebSmsService webSmsService() {
    return new MockWebSmsService();
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    JavaTimeModule module = new JavaTimeModule();
    module.addSerializer(OffsetDateTime.class, JSR310DateTimeSerializer.INSTANCE);
    module.addSerializer(ZonedDateTime.class, JSR310DateTimeSerializer.INSTANCE);
    module.addSerializer(LocalDateTime.class, JSR310DateTimeSerializer.INSTANCE);
    module.addSerializer(Instant.class, JSR310DateTimeSerializer.INSTANCE);
    module.addDeserializer(LocalDate.class, JSR310LocalDateDeserializer.INSTANCE);
    mapper.registerModule(module);
    return mapper;
  }
}
