package be.dries.jmstest.config;

import be.dries.jmstest.listener.MailMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Configuration
public class JmsConfig {
  @Resource(mappedName = "openejb:Resource/XAConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "openejb:Resource/MailQueue")
  private Queue queue;

  @Bean
  public Queue mailQueue() {
    return queue;
  }

  @Bean
  public DefaultMessageListenerContainer mailQueueListenerContainer(PlatformTransactionManager transactionManager) {
    DefaultMessageListenerContainer mailQueueListenerContainer = new DefaultMessageListenerContainer();
    mailQueueListenerContainer.setConnectionFactory(connectionFactory);
    mailQueueListenerContainer.setDestination(queue);
    mailQueueListenerContainer.setMessageListener(messageListener());
    mailQueueListenerContainer.setConcurrentConsumers(10);
    mailQueueListenerContainer.setMaxConcurrentConsumers(20);
    mailQueueListenerContainer.setTransactionManager(transactionManager);
    mailQueueListenerContainer.setSessionTransacted(true);

    return mailQueueListenerContainer;
  }

  @Bean
  public MailMessageListener messageListener() {
    return new MailMessageListener();
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    return new JmsTemplate(connectionFactory);
  }
}
