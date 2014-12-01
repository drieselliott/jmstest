package be.dries.jmstest.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import be.dries.jmstest.domain.Message;

public class MessageRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @PersistenceContext
  private EntityManager entityManager;

  public void saveMessage(String message) {
    jdbcTemplate.update("INSERT INTO MESSAGE (TEXT) VALUES (:text)", message);
    entityManager.persist(new Message(message + message + message));
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveInNewTransaction(String message) {
    saveMessage(message);
  }
}
