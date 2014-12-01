package be.dries.jmstest.config;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;

import be.dries.jmstest.listener.ManagementMessageListener;

@Configuration
public class JmsManagementConfig {
  @Resource(mappedName = "openejb:Resource/XAConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "openejb:Resource/RmaRoleAssignmentQueue")
  private Queue managementQueue;

  @Bean
  public Queue managementQueue() {
    return managementQueue;
  }

  @Bean
  public DefaultMessageListenerContainer managementQueueListenerContainer(PlatformTransactionManager transactionManager) {
    DefaultMessageListenerContainer managementQueueListenerContainer = new DefaultMessageListenerContainer();
    managementQueueListenerContainer.setConnectionFactory(connectionFactory);
    managementQueueListenerContainer.setDestination(managementQueue());
    managementQueueListenerContainer.setMessageListener(managementMessageListener());
    managementQueueListenerContainer.setConcurrentConsumers(10);
    managementQueueListenerContainer.setMaxConcurrentConsumers(20);
    managementQueueListenerContainer.setTransactionManager(transactionManager);

    return managementQueueListenerContainer;
  }

  @Bean
  public ManagementMessageListener managementMessageListener() {
    return new ManagementMessageListener();
  }

  @Bean
  public JmsTemplate managementJmsTemplate() {
    return new JmsTemplate(connectionFactory);
  }
}
