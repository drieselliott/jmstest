package be.dries.jmstest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {
  @Bean(name = { "transactionManager" })
  public PlatformTransactionManager platformTransactionManager() {
    return new JtaTransactionManager();
  }
}
