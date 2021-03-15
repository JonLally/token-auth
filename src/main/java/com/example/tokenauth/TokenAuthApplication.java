package com.example.tokenauth;

import com.example.tokenauth.security.SecurityConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class, MultipartAutoConfiguration.class,
    RestTemplateAutoConfiguration.class, TaskExecutionAutoConfiguration.class, TaskSchedulingAutoConfiguration.class,
    WebSocketServletAutoConfiguration.class, SecurityAutoConfiguration.class})
@Import({ SecurityConfiguration.class })
public class TokenAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(TokenAuthApplication.class, args);
  }

}
