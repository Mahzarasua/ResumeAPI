package com.izars;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = AuthAPI.class)
class AuthAPITest {
    @Test
    public void contextLoads() {
    }

}