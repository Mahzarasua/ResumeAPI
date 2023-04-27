package com.izars.resumeapi.auth.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyUserroleData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class UserroleTest {
    private static Userrole r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyUserroleData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        Userrole test = new Userrole(r.getId());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);

        test = new Userrole();
        assertAndLogResponse(test);
    }

}