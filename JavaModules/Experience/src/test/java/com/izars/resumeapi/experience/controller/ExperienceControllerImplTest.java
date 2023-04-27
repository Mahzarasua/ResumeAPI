package com.izars.resumeapi.experience.controller;

import com.izars.resumeapi.configuration.util.AbstractTest;
import com.izars.resumeapi.experience.domain.ExperienceRequest;
import com.izars.resumeapi.experience.domain.ExperienceResponse;
import com.izars.resumeapi.experience.mapper.ExperienceMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.generateRandomIdString;
import static com.izars.resumeapi.experience.DummyTestExperienceData.dummyExperienceRequestData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExperienceControllerImplTest extends AbstractTest {
    private static final String uri = "/api/v1/experience/";

    private static final String resumeId = "1d84b77d-7670-4a62-adc1-5de5b24cb75d";
    private static final String idCreatedDeleted = String.valueOf(generateRandomIdString());

    private static final String url = uri + resumeId;

    private static final String initialExp = "{\"experienceList\":[{\"id\":\"268fd1cd-8566-4bea-b170-01f53b3aae91\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"title\":\"Backend Developer\",\"company\":\"4th Source\",\"currentJob\":true,\"description\":\"Design and develop the backend for the enterprise projects.\",\"startDate\":\"2020-11-01\",\"endDate\":null}]}";

    private static ExperienceRequest r;

    @BeforeEach
    public void setUp() {
        super.setUp();
        r = dummyExperienceRequestData();
    }

    private ExperienceResponse getRecordByResumeId() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        ExperienceResponse response = super.mapFromJson(content, ExperienceResponse.class);
        assertFalse(response.getExperienceList().isEmpty());
        log.info("Resume found with resourceId: {}", resumeId);
        assertAndLogResponse(response);
        return response;
    }

    @Test
    @Order(1)
    public void getByResumeId() throws Exception {
        getRecordByResumeId();
    }

    @Test
    @Order(2)
    public void createRecord() throws Exception {
        ExperienceResponse response = getRecordByResumeId();
        int status;
        String content;
        MvcResult mvcResult;

        ExperienceMapper mapper = new ExperienceMapper();
        ExperienceRequest request = mapper.map(response, ExperienceRequest.class);
        assertAndLogResponse(request);
        request.getExperienceList().get(0).setId(null);
        String json = super.mapToJson(request);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        status = mvcResult.getResponse().getStatus();

        assertEquals(201, status);
        content = mvcResult.getResponse().getContentAsString();

        assertAndLogResponse(content);

        response = super.mapFromJson(content, ExperienceResponse.class);
        assertFalse(response.getExperienceList().isEmpty());
        log.info("Resume found with resourceId: {}", resumeId);
        assertAndLogResponse(response);
    }

    @Test
    @Order(3)
    public void deleteRecord() throws Exception {
        ExperienceResponse response = getRecordByResumeId();
        ExperienceMapper mapper = new ExperienceMapper();
        ExperienceRequest request = mapper.map(response, ExperienceRequest.class);
        assertAndLogResponse(request);

        String id = String.valueOf(request.getExperienceList().get(0).getId());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("id", id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("resumeId"));
        assertTrue(content.contains("id"));
        log.info("Entry deleted for: {}", content);
    }

    @Test
    @Order(4)
    public void createDummyRecord() throws Exception {
        ExperienceMapper map = new ExperienceMapper();
        ExperienceRequest request = r;
        assertAndLogResponse(request);
        request.getExperienceList().get(0).setId(UUID.fromString(idCreatedDeleted));
        request.getExperienceList().get(0).setResumeId(UUID.fromString(resumeId));

        String json = super.mapToJson(request);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        ExperienceResponse response = super.mapFromJson(content, ExperienceResponse.class);
        log.info("Resume found with resourceId: {}", resumeId);
        log.info("Entry created for: {}", idCreatedDeleted);
        assertAndLogResponse(response);
    }

    @Test
    @Order(5)
    public void deleteDummyRecord() throws Exception {
        String id = idCreatedDeleted;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("id", id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        assertTrue(content.contains("resumeId"));
        assertTrue(content.contains("id"));
        log.info("Entry deleted for: {}", content);
    }

    @Test
    @Order(6)
    public void setOriginal() throws Exception {
        ExperienceRequest request = super.mapFromJson(initialExp, ExperienceRequest.class);
        assertAndLogResponse(request);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("resumeId"));
        log.info("All records deleted for: {}", content);

        String json = super.mapToJson(request);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        status = mvcResult.getResponse().getStatus();

        assertEquals(201, status);
        content = mvcResult.getResponse().getContentAsString();

        assertAndLogResponse(content);
    }

    @Test
    @Order(7)
    public void getByResumeIdAndThrowNotRecordFound() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        String expectedMessage = "No Record was found";
        assertTrue(content.contains(expectedMessage));
    }

    @Test
    @Order(8)
    public void deleteByResumeIdAndThrowNotRecordFound() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        String expectedMessage = "No Education record was found";
        assertTrue(content.contains(expectedMessage));
    }

}