package be.dries.jmstest.config;

import be.dries.jmstest.repository.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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

  @Bean
  public EntityManagerFactory entityManagerFactoryNar() {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put("hibernate.transaction.manager_lookup_class", "org.apache.openejb.hibernate.TransactionManagerLookup");
    properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
    properties.put("hibernate.show_sql", "false");

    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setPersistenceXmlLocation("classpath:META-INF/persistence_nar.xml");
    entityManagerFactoryBean.setJtaDataSource(dataSource());
    entityManagerFactoryBean.getJpaPropertyMap().putAll(properties);
    entityManagerFactoryBean.afterPropertiesSet();

    return entityManagerFactoryBean.getObject();
  }
}
