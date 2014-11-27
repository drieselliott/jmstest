package be.dries.jmstest.repository;

import be.dries.jmstest.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MessageRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @PersistenceContext
  private EntityManager entityManager;

  public void saveMessage(String message) {
    jdbcTemplate.update("INSERT INTO MESSAGE (TEXT) VALUES (:text)", message);
    entityManager.persist(new Message(message + message + message));
  }
}
