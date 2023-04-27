package com.izars.resumeapi.education.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.education.DummyTestEducationData.dummyEducationDomainData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class EducationDomainTest {

    private static EducationDomain r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyEducationDomainData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void EducationDomain() throws JsonProcessingException {
        EducationDomain test = new EducationDomain(r.getId(), r.getResumeId(), r.getName(), r.getCareer(), r.getDegree(), r.getStartDate(), r.getEndDate());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);
    }

}