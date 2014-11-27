package be.dries.jmstest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class MessageRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void saveMessage(String message) {
    jdbcTemplate.update("INSERT INTO MESSAGE (TEXT) VALUES (:text)", message);
  }
}
