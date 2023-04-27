package com.izars.resumeapi.auth.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummySkillData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class SkillTest {

    private static Skill r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummySkillData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        Skill test = new Skill(r.getId(), r.getName(), r.getPercentage(), r.getType(), r.getResume());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);
    }

}