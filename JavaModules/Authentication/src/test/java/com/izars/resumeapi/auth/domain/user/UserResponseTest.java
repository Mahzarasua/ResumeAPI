package com.izars.resumeapi.auth.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyUserResponseData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class UserResponseTest {

    private static UserResponse r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = dummyUserResponseData();
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        UserResponse test = new UserResponse(r.getId(), r.getUsername(), r.getPassword(), r.isActive(), r.getRoles());
        assertThat(test).usingRecursiveComparison().isEqualTo(r);
        assertAndLogResponse(test);

        test = new UserResponse();
        assertAndLogResponse(test);
    }

}