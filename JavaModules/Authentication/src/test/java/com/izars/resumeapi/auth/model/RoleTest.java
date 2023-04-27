package com.izars.resumeapi.auth.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyRoleData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class RoleTest {
    private static Role r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyRoleData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        Role test = new Role(r.getId(), r.getRole());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);

        test = new Role();
        assertAndLogResponse(test);
    }

}