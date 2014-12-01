package be.dries.jmstest.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.dries.jmstest.annotation.TestAnnotation;
import be.dries.jmstest.repository.MessageRepository;

@Service
public class MessageService {
  @Autowired
  @Qualifier("managementJmsTemplate")
  private JmsTemplate managementJmsTemplate;
  @Autowired
  @Qualifier("mailJmsTemplate")
  private JmsTemplate mailJmsTemplate;
  @Autowired
  @Qualifier("mailQueue")
  private Queue mailQueue;
  @Autowired
  @Qualifier("managementQueue")
  private Queue managementQueue;
  @Autowired
  private MessageRepository messageRepository;

  @Transactional
  @TestAnnotation
  public void sendMessage(String message) {
    mailJmsTemplate.send(mailQueue, new SimpleMessageCreator(message));
    managementJmsTemplate.send(managementQueue, new SimpleMessageCreator(message));

    messageRepository.saveMessage(message);
    messageRepository.saveInNewTransaction(message);
  }

  @Transactional
  public void sendErrorMessage(String message) {
    mailJmsTemplate.send(mailQueue, new SimpleMessageCreator(message));

    messageRepository.saveMessage(message);

    throw new RuntimeException("Nothing will be send or persisted, because this exception causes the transaction to rollback");
  }

  private class SimpleMessageCreator implements MessageCreator {
    private String message;

    public SimpleMessageCreator(String message) {
      this.message = message;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
      ObjectMessage jmsMessage = session.createObjectMessage();
      jmsMessage.setObject(message);

      return jmsMessage;
    }
  }
}
