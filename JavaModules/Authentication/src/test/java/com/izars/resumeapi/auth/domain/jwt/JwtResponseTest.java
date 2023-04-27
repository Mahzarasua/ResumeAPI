package com.izars.resumeapi.auth.domain.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyJwtResponseData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class JwtResponseTest {
    private static JwtResponse r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyJwtResponseData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        JwtResponse test = new JwtResponse(r.getJwtToken());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);

        test = new JwtResponse();
        assertAndLogResponse(test);
    }

}