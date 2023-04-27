package com.izars.resumeapi.experience.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.experience.DummyTestExperienceData.dummyExperienceDomainData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class ExperienceDomainTest {

    private static ExperienceDomain r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyExperienceDomainData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void ExperienceDomain() throws JsonProcessingException {
        ExperienceDomain test = new ExperienceDomain(r.getId(), r.getResumeId(), r.getTitle(), r.getCompany(), r.isCurrentJob(), r.getDescription(), r.getStartDate(), r.getEndDate());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);
    }

}