package by.bsu.rfe.smsservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("by.bsu.rfe.smsservice.cache.**")
public class CacheConfiguration {

}
