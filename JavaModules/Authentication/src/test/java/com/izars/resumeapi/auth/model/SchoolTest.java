package com.izars.resumeapi.auth.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummySchoolData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class SchoolTest {

    private static School r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummySchoolData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        School test = new School(r.getId(), r.getName(), r.getCareer(), r.getDegree(), r.getStartDate(), r.getEndDate(), r.getResume());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);
    }

}