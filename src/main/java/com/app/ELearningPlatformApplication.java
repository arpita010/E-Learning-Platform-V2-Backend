package com.app;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Transactional
public class ELearningPlatformApplication {

  public static void main(String[] args) {
    SpringApplication.run(ELearningPlatformApplication.class, args);
  }
}
