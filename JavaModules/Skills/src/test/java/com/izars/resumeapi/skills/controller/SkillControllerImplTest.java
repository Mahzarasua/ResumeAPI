package com.izars.resumeapi.skills.controller;

import com.izars.resumeapi.configuration.util.AbstractTest;
import com.izars.resumeapi.skills.domain.SkillRequest;
import com.izars.resumeapi.skills.domain.SkillResponse;
import com.izars.resumeapi.skills.mapper.SkillMapper;
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
import static com.izars.resumeapi.skills.DummyTestSkillsData.dummySkillRequestData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SkillControllerImplTest extends AbstractTest {

    private static final String uri = "/api/v1/skill/";

    private static final String resumeId = "1d84b77d-7670-4a62-adc1-5de5b24cb75d";
    private static final String idCreatedDeleted = String.valueOf(generateRandomIdString());

    private static final String url = uri + resumeId;

    private static final String initialSkills = "{\"skillList\":[{\"id\":\"94e0e1ea-b7cc-4144-addd-5def8d0d572d\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"name\":\"java\",\"percentage\":85,\"type\":\"tech skill\"},{\"id\":\"beed7158-c673-4170-8437-9cfb590f372c\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"name\":\"Spanish (native)\",\"percentage\":90,\"type\":\"language skill\"},{\"id\":\"d3388539-6ede-4ade-a4bf-99653dbe5ff5\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"name\":\"English\",\"percentage\":80,\"type\":\"language skill\"}]}";

    private static SkillRequest r;

    @BeforeEach
    public void setUp() {
        super.setUp();
        r = dummySkillRequestData();
    }

    private SkillResponse getSkillsByResumeId() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        SkillResponse response = super.mapFromJson(content, SkillResponse.class);
        assertFalse(response.getSkillList().isEmpty());
        log.info("Resume found with resourceId: {}", resumeId);
        assertAndLogResponse(response);
        return response;
    }

    @Test
    @Order(1)
    public void getByResumeId() throws Exception {
        getSkillsByResumeId();
    }

    @Test
    @Order(2)
    public void createRecord() throws Exception {
        SkillResponse response = getSkillsByResumeId();
        int status;
        String content;
        MvcResult mvcResult;

        SkillMapper mapper = new SkillMapper();
        SkillRequest request = mapper.map(response, SkillRequest.class);
        assertAndLogResponse(request);
        request.getSkillList().get(0).setId(null);
        String json = super.mapToJson(request);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        status = mvcResult.getResponse().getStatus();

        assertEquals(201, status);
        content = mvcResult.getResponse().getContentAsString();

        assertAndLogResponse(content);

        response = super.mapFromJson(content, SkillResponse.class);
        assertFalse(response.getSkillList().isEmpty());
        log.info("Resume found with resourceId: {}", resumeId);
        assertAndLogResponse(response);
    }

    @Test
    @Order(3)
    public void deleteRecord() throws Exception {
        SkillResponse response = getSkillsByResumeId();
        SkillMapper mapper = new SkillMapper();
        SkillRequest request = mapper.map(response, SkillRequest.class);
        assertAndLogResponse(request);

        String id = String.valueOf(request.getSkillList().get(0).getId());

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
        SkillMapper map = new SkillMapper();
        SkillRequest request = r;
        assertAndLogResponse(request);
        request.getSkillList().get(0).setId(UUID.fromString(idCreatedDeleted));
        request.getSkillList().get(0).setResumeId(UUID.fromString(resumeId));

        String json = super.mapToJson(request);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        SkillResponse response = super.mapFromJson(content, SkillResponse.class);
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
        SkillRequest request = super.mapFromJson(initialSkills, SkillRequest.class);
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
        String expectedMessage = "No Skill record was found";
        assertTrue(content.contains(expectedMessage));
    }

}