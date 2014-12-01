package be.dries.jmstest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "be.dries.jmstest.aspect")
public class AspectConfig {
}
