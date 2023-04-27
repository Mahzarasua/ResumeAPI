package com.izars.resumeapi.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.izars.resumeapi.auth.config.CustomAuthenticationManager;
import com.izars.resumeapi.auth.config.JwtTokenUtil;
import com.izars.resumeapi.auth.domain.jwt.JwtRequest;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.auth.validator.JwtRequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyJwtRequestData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class JwtAuthenticationControllerImplTest {
    private static JwtAuthenticationController r;

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtRequestValidator validator;

    @BeforeEach
    public void init() {
        r = new JwtAuthenticationController(authenticationManager, jwtTokenUtil, validator);
    }

    @Test
    public void testBadCredentials() throws JsonProcessingException {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            r.createAuthenticationToken(dummyJwtRequestData());
        });

        String expectedMessage = "error";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));
        assertAndLogResponse(exception.getErrorDetails());

    }

    @Test
    public void test() {
        JwtRequest request = new JwtRequest("zars", "Welcome1");
        r.createAuthenticationToken(request);
    }
}