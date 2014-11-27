package be.dries.jmstest.config;

import be.dries.jmstest.repository.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {
  @Bean
  public DataSource dataSource() {
    return new JndiDataSourceLookup().getDataSource("openejb:Resource/jdbc/OracleNarDs");
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  public MessageRepository messageRepository() {
    return new MessageRepository();
  }
}
