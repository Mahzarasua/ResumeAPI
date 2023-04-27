package com.izars.resumeapi.education.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.education.DummyTestEducationData.dummyEducationDomainData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class EducationRequestTest {

    private static List<EducationDomain> r;
    private static EducationRequest sr;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = Collections.singletonList(dummyEducationDomainData());
        sr = new EducationRequest(r);
        log.info("Object generated: {}", sr);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        EducationRequest test = new EducationRequest(r);
        assertThat(test).usingRecursiveComparison().isEqualTo(sr);
        assertAndLogResponse(test);

        test = new EducationRequest();
        assertNotNull(test);
    }

}