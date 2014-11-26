package be.dries.jmstest.config;

import be.dries.jmstest.controller.MessageController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class MvcConfig {
  @Bean
  public MessageController messageController() {
    return new MessageController();
  }
}
