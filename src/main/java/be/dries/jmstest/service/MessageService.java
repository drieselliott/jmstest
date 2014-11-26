package be.dries.jmstest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

@Service
public class MessageService {
  @Autowired
  private JmsTemplate jmsTemplate;
  @Autowired
  @Qualifier("mailQueue")
  private Queue queue;

  @Transactional
  public void sendMessage(String message) {
    jmsTemplate.send(queue, new SimpleMessageCreator(message));
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
