package be.dries.jmstest.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;

import be.dries.jmstest.repository.MessageRepository;

public class ManagementMessageListener implements MessageListener {
  @Autowired
  private MessageRepository messageRepository;

  @Override
  public void onMessage(Message message) {
    if (message instanceof ObjectMessage) {
      try {
        ObjectMessage jmsMessage = (ObjectMessage) message;
        String textMessage = (String) jmsMessage.getObject();

        messageRepository.saveMessage("management" + textMessage + textMessage);
        System.out.println("management" + textMessage);
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
  }
}
