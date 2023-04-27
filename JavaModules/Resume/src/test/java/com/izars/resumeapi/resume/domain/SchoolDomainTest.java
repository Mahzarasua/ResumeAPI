package com.izars.resumeapi.resume.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.resume.DummyTestResumeData.dummySchoolDomainData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class SchoolDomainTest {

    private static SchoolDomain r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummySchoolDomainData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        SchoolDomain test = new SchoolDomain(r.getId(), r.getResumeId(), r.getName(), r.getCareer(), r.getStartDate(), r.getEndDate(), r.getDegree());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);
    }

}