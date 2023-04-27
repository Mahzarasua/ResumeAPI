package com.izars.resumeapi.experience.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.experience.DummyTestExperienceData.dummyExperienceDomainData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class ExperienceRequestTest {
    private static List<ExperienceDomain> r;
    private static ExperienceRequest sr;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = Collections.singletonList(dummyExperienceDomainData());
        sr = new ExperienceRequest(r);
        log.info("Object generated: {}", sr);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        ExperienceRequest test = new ExperienceRequest(r);
        assertThat(test).usingRecursiveComparison().isEqualTo(sr);
        assertAndLogResponse(test);

        test = new ExperienceRequest();
        assertNotNull(test);
    }

}