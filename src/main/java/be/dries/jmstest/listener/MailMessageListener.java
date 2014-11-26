package be.dries.jmstest.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class MailMessageListener implements MessageListener {
  @Override
  public void onMessage(Message message) {
    if (message instanceof ObjectMessage) {
      try {
        ObjectMessage jmsMessage = (ObjectMessage) message;
        String textMessage = (String) jmsMessage.getObject();

        System.out.println(textMessage);
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
  }
}
