package com.izars;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = ConfigServerApp.class)
class ConfigServerAppTest {
    @Test
    public void contextLoads() {
    }
}