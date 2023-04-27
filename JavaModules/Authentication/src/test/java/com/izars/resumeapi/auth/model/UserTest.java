package com.izars.resumeapi.auth.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyUserData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class UserTest {
    private static User r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyUserData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        User test = new User(r.getId(), r.getUsername(), r.getPassword(), r.isActive(), r.getRoles());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);

        test = new User();
        assertAndLogResponse(test);
    }

}