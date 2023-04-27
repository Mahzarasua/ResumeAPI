package com.izars.resumeapi.skills.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.skills.DummyTestSkillsData.dummySkillDomainData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class SkillRequestTest {

    private static List<SkillDomain> r;
    private static SkillRequest sr;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = Collections.singletonList(dummySkillDomainData());
        sr = new SkillRequest(r);
        log.info("Object generated: {}", sr);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        SkillRequest test = new SkillRequest(r);
        assertThat(test).usingRecursiveComparison().isEqualTo(sr);
        assertAndLogResponse(test);

        test = new SkillRequest();
        assertNotNull(test);
    }

    @Test
    public void testBuilder() throws JsonProcessingException {
        SkillRequest test = SkillRequest.builder().skillList(r).build();
        assertThat(test).usingRecursiveComparison().isEqualTo(sr);
        assertAndLogResponse(test);
    }

}