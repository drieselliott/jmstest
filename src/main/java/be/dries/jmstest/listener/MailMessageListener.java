package be.dries.jmstest.listener;

import be.dries.jmstest.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class MailMessageListener implements MessageListener {
  @Autowired
  private MessageRepository messageRepository;

  @Override
  public void onMessage(Message message) {
    if (message instanceof ObjectMessage) {
      try {
        ObjectMessage jmsMessage = (ObjectMessage) message;
        String textMessage = (String) jmsMessage.getObject();

        messageRepository.saveMessage(textMessage + textMessage);
        System.out.println(textMessage);
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
  }
}
