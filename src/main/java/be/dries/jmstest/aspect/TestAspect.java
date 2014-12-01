package be.dries.jmstest.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dries.jmstest.repository.MessageRepository;

@Aspect
@Component
public class TestAspect {

  @Autowired
  private MessageRepository messageRepository;

  @Around("execution(@be.dries.jmstest.annotation.TestAnnotation * be.dries.jmstest.service.MessageService.*(..))")
  public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
    return auditCall(joinPoint);
  }

  private Object auditCall(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("ok");
    messageRepository.saveMessage("audit message");
    messageRepository.saveInNewTransaction("audit message");

    return joinPoint.proceed();
  }
}
