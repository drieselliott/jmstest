package be.dries.jmstest.config;

import be.dries.jmstest.service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
  @Bean
  public MessageService messageService() {
    return new MessageService();
  }
}
