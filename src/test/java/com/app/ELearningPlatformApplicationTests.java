package com.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ELearningPlatformApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  void test() {
    String url = "/api/v1/classroom/1/user/1/join/";
    String reg = "^/api/v1/classroom/\\d+/user/\\d+/join/.*$";
    log.info("Regex Matches : {}", url.matches(reg));
  }
}
