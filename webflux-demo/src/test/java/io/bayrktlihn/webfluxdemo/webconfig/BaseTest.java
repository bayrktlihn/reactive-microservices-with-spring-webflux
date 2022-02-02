package io.bayrktlihn.webfluxdemo.webconfig;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class BaseTest {

  @Test
  void contextLoads() {
  }

}
