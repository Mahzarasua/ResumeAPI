package com.izars.resumeapi.auth.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyWorkExpData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class WorkExperienceTest {
    private static WorkExperience r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyWorkExpData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        WorkExperience test = new WorkExperience(r.getId(), r.getTitle(), r.getCompany(), r.isCurrentJob(), r.getDescription(), r.getStartDate(), r.getEndDate(), r.getResume());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);
    }

}