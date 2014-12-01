package be.dries.jmstest.config;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;

import be.dries.jmstest.listener.MailMessageListener;

@Configuration
public class JmsMailConfig {
  @Resource(mappedName = "openejb:Resource/XAConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "openejb:Resource/MailQueue")
  private Queue mailQueue;

  @Bean
  public Queue mailQueue() {
    return mailQueue;
  }

  @Bean
  public DefaultMessageListenerContainer mailQueueListenerContainer(PlatformTransactionManager transactionManager) {
    DefaultMessageListenerContainer mailQueueListenerContainer = new DefaultMessageListenerContainer();
    mailQueueListenerContainer.setConnectionFactory(connectionFactory);
    mailQueueListenerContainer.setDestination(mailQueue());
    mailQueueListenerContainer.setMessageListener(mailMessageListener());
    mailQueueListenerContainer.setConcurrentConsumers(10);
    mailQueueListenerContainer.setMaxConcurrentConsumers(20);
    mailQueueListenerContainer.setTransactionManager(transactionManager);

    return mailQueueListenerContainer;
  }

  @Bean
  public MailMessageListener mailMessageListener() {
    return new MailMessageListener();
  }

  @Bean
  public JmsTemplate mailJmsTemplate() {
    return new JmsTemplate(connectionFactory);
  }
}
