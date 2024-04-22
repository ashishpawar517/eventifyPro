package com.aashishlabs.eventifypro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventifyProApplicationTests {

  @Test
  void contextLoads() {
    Assertions.assertEquals(7, 7);
  }

  @Test
  void sampleTest() {
    Assertions.assertEquals(77, 7);
  }
}
