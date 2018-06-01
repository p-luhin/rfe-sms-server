package by.bsu.rfe.smsservice.config;

import static org.dozer.loader.api.TypeMappingOptions.oneWay;

import by.bsu.rfe.smsservice.common.dto.EmailTemplateDTO;
import by.bsu.rfe.smsservice.common.dto.ExternalApplicationDTO;
import by.bsu.rfe.smsservice.common.dto.StatisticsDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.entity.EmailEntity;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfiguration {

  private BeanMappingBuilder mapSmsQueue() {
    return new BeanMappingBuilder() {
      @Override
      protected void configure() {
        mapping(
            SmsQueueEntity.class,
            SmsQueueRequestDTO.class,
            oneWay())
            .fields(
                "credentials.sender",
                "senderName"
            )
            .fields(
                "message",
                "content"
            );
      }
    };
  }

  private BeanMappingBuilder mapStatistics() {
    return new BeanMappingBuilder() {
      @Override
      protected void configure() {
        mapping(
            StatisticsEntity.class,
            StatisticsDTO.class,
            oneWay())
            .fields(
                "sender",
                "senderName"
            );
      }
    };
  }

  private BeanMappingBuilder mapEmailEntities() {
    return new BeanMappingBuilder() {
      @Override
      protected void configure() {
        mapping(
            EmailEntity.class,
            EmailTemplateDTO.class,
            oneWay())
            .fields(
                "smsTemplate.smsType",
                "smsType"
            );
      }
    };
  }

  private BeanMappingBuilder mapExternalApplications() {
    return new BeanMappingBuilder() {
      @Override
      protected void configure() {
        mapping(
            ExternalApplicationEntity.class,
            ExternalApplicationDTO.class,
            oneWay())
            .fields(
                "defaultCredentials.sender",
                "credentialsSenderName"
            );
      }
    };
  }

  @Bean
  public Mapper dozerMapper() {
    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    dozerBeanMapper.addMapping(mapSmsQueue());
    dozerBeanMapper.addMapping(mapEmailEntities());
    dozerBeanMapper.addMapping(mapExternalApplications());
    dozerBeanMapper.addMapping(mapStatistics());

    return dozerBeanMapper;
  }

}
