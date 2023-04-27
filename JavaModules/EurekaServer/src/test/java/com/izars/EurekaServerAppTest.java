package com.izars;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = EurekaServerApp.class)
class EurekaServerAppTest {
    @Test
    public void contextLoads() {
    }

}