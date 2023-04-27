package com.izars.resumeapi.auth.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.izars.resumeapi.auth.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyUserData;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class MyUserDetailsTest {

    private static final User model = dummyUserData();
    private MyUserDetails r;

    @BeforeEach
    public void init() {
        log.info("Starting init");
        r = new MyUserDetails(model);
        log.info("Object generated: {}", r);
        log.info("Ending init");
    }

    @Test
    public void test() throws JsonProcessingException {
        assertAndLogResponse(r);
        assertNotNull(r.getPassword());
        assertNotNull(r.getUsername());
        assertNull(r.getAuthorities());
        assertFalse(r.isEnabled());
        assertTrue(r.isAccountNonExpired());
        assertTrue(r.isCredentialsNonExpired());
        assertTrue(r.isAccountNonLocked());
    }

}