package com.izars.resumeapi.resume.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.generateRandomIdString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class ResumeIdResponseTest {

    private static final UUID resumeId = generateRandomIdString();
    private static ResumeIdResponse r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = new ResumeIdResponse(resumeId);
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        ResumeIdResponse test = new ResumeIdResponse(resumeId);
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);

        test = new ResumeIdResponse();
        assertAndLogResponse(test);
    }

}