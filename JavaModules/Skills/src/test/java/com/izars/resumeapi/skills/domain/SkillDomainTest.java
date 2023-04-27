package com.izars.resumeapi.skills.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.skills.DummyTestSkillsData.dummySkillDomainData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class SkillDomainTest {

    private static SkillDomain r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummySkillDomainData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        SkillDomain test = new SkillDomain(r.getId(), r.getResumeId(), r.getName(), r.getPercentage(), r.getType());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);
    }

}