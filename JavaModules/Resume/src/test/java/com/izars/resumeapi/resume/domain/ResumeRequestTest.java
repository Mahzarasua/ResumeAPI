package com.izars.resumeapi.resume.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.resume.DummyTestResumeData.dummyResumeRequestData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class ResumeRequestTest {

    private static ResumeRequest r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyResumeRequestData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        ResumeRequest test = new ResumeRequest(r.getId(), r.getFirstName(), r.getLastName(), r.getTitle(), r.getCity(), r.getState(), r.getCountry(), r.getEmail(), r.getPhone(), r.getSummary(), r.getCreationDate(), r.getSkills(), r.getSchools(), r.getWorkExperiences());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);

        test = new ResumeRequest();
        assertAndLogResponse(test);
    }
}