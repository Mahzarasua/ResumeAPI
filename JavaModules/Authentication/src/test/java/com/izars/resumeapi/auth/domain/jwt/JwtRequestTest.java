package com.izars.resumeapi.auth.domain.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyJwtRequestData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class JwtRequestTest {
    private static JwtRequest r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyJwtRequestData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        JwtRequest test = new JwtRequest(r.getUsername(), r.getPassword());

        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);

        test = new JwtRequest();
        assertAndLogResponse(test);
    }
}