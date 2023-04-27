package com.izars.resumeapi.education;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = EducationAPI.class)
class EducationAPITest {
    @Test
    public void contextLoads() {
    }
}