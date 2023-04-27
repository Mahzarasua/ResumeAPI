package com.izars.resumeapi.resume.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.izars.ResumeAPI;
import com.izars.resumeapi.configuration.util.AbstractTest;
import com.izars.resumeapi.resume.domain.ResumeIdResponse;
import com.izars.resumeapi.resume.domain.ResumeRequest;
import com.izars.resumeapi.resume.domain.ResumeResponse;
import com.izars.resumeapi.resume.mapper.ResumeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.generateRandomIdString;
import static com.izars.resumeapi.resume.DummyTestResumeData.dummyResumeRequestData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = ResumeAPI.class)
class ResumeControllerImplTest extends AbstractTest {
    private static final String uri = "/api/v1/resume/";

    private static final String resumeId = "1d84b77d-7670-4a62-adc1-5de5b24cb75d";
    private static final String idCreatedDeleted = String.valueOf(generateRandomIdString());

    private static final String url = uri + resumeId;

    private static final String initialResume = "{\"id\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"firstName\":\"Miguel Alejandro\",\"lastName\":\"Hernandez Zarasua\",\"title\":\"Backend Developer\",\"city\":\"Colima\",\"state\":\"Colima\",\"country\":\"Mexico\",\"email\":\"mahzarasua@outlook.com\",\"phone\":\"+523121557091\",\"summary\":\"Java developer\",\"creationDate\":\"2022-01-03\",\"skills\":[{\"id\":\"94e0e1ea-b7cc-4144-addd-5def8d0d572d\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"name\":\"java\",\"percentage\":85,\"type\":\"tech skill\"},{\"id\":\"beed7158-c673-4170-8437-9cfb590f372c\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"name\":\"Spanish (native)\",\"percentage\":90,\"type\":\"language skill\"},{\"id\":\"d3388539-6ede-4ade-a4bf-99653dbe5ff5\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"name\":\"English\",\"percentage\":80,\"type\":\"language skill\"}],\"schools\":[{\"id\":\"96a970f1-7395-4e91-85ff-685915e3eaaf\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"name\":\"Instituto Tecnologico de Lazaro Cardenas\",\"career\":\"Computer Systems Engineering\",\"startDate\":\"2016-08-01\",\"endDate\":\"2021-12-01\",\"degree\":\"Bachelor\"}],\"workExperiences\":[{\"id\":\"233df173-e1c7-4617-bcd6-663ff250c590\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"title\":\"OCAQHAKQ\",\"company\":\"PUSQWWWY\",\"startDate\":\"2023-04-26\",\"endDate\":null,\"currentJob\":true,\"description\":\"RRMMZGAM\"},{\"id\":\"268fd1cd-8566-4bea-b170-01f53b3aae91\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"title\":\"Backend Developer\",\"company\":\"4th Source\",\"startDate\":\"2020-11-01\",\"endDate\":null,\"currentJob\":true,\"description\":\"Design and develop the backend for the enterprise projects.\"},{\"id\":\"7f380f86-668a-4fb9-b3d0-312c7754ab20\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"title\":\"IZVDNOOF\",\"company\":\"SMLKEEHL\",\"startDate\":\"2023-04-26\",\"endDate\":null,\"currentJob\":true,\"description\":\"EFSFRRYC\"},{\"id\":\"c59e9e1b-b808-4415-bbcb-9dbd1681e4ea\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"title\":\"EOFLOLWB\",\"company\":\"XDBKCJYQ\",\"startDate\":\"2023-04-26\",\"endDate\":null,\"currentJob\":true,\"description\":\"TTPYABDG\"},{\"id\":\"d10230be-7acd-43ef-811c-538083331674\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"title\":\"Backend Developer\",\"company\":\"4th Source\",\"startDate\":\"2020-11-01\",\"endDate\":null,\"currentJob\":true,\"description\":\"Design and develop the backend for the enterprise projects.\"},{\"id\":\"d2303a24-76c8-41b8-8fdb-59607f34dd9d\",\"resumeId\":\"1d84b77d-7670-4a62-adc1-5de5b24cb75d\",\"title\":\"Backend Developer\",\"company\":\"4th Source\",\"startDate\":\"2020-11-01\",\"endDate\":null,\"currentJob\":true,\"description\":\"Design and develop the backend for the enterprise projects.\"}]}";

    private static ResumeRequest r;

    private static ResumeRequest setChildTables(ResumeRequest request) {
        if (request.getSkills() != null)
            for (int i = 0; i < request.getSkills().size(); i++) {
                request.getSkills().get(i).setId(null);
                request.getSkills().get(i).setResumeId(null);
            }

        if (request.getSchools() != null)
            for (int i = 0; i < request.getSchools().size(); i++) {
                request.getSchools().get(i).setId(null);
                request.getSchools().get(i).setResumeId(null);
            }

        if (request.getWorkExperiences() != null)
            for (int i = 0; i < request.getWorkExperiences().size(); i++) {
                request.getWorkExperiences().get(i).setId(null);
                request.getWorkExperiences().get(i).setResumeId(null);
            }
        return request;
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
        r = dummyResumeRequestData();
    }

    private List<ResumeResponse> getRecords() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        ResumeMapper mapper = new ResumeMapper();
        TypeReference<List<ResumeResponse>> clazz = new TypeReference<>() {
        };
        List<ResumeResponse> response = super.mapFromJsonList(content, clazz);
        assertNotNull(response);
        log.info("Resume found with resourceId: {}", resumeId);
        assertAndLogResponse(response);
        return response;
    }

    private ResumeResponse getRecordByResumeId() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        ResumeResponse response = super.mapFromJson(content, ResumeResponse.class);
        assertNotNull(response);
        log.info("Resume found with resourceId: {}", resumeId);
        assertAndLogResponse(response);
        return response;
    }

    @Test
    @Order(1)
    public void getAllResumes() throws Exception {
        getRecords();
    }

    @Test
    @Order(2)
    public void getByResumeId() throws Exception {
        getRecordByResumeId();
    }

    @Test
    @Order(3)
    public void createRecord() throws Exception {
        ResumeResponse response = getRecordByResumeId();
        int status;
        String content;
        MvcResult mvcResult;

        ResumeMapper mapper = new ResumeMapper();
        ResumeRequest request = mapper.map(response, ResumeRequest.class);
        assertAndLogResponse(request);
        request.setId(null);

        setChildTables(request);

        String json = super.mapToJson(request);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        status = mvcResult.getResponse().getStatus();

        assertEquals(201, status);
        content = mvcResult.getResponse().getContentAsString();

        assertAndLogResponse(content);

        ResumeIdResponse resumeIdResponse = super.mapFromJson(content, ResumeIdResponse.class);
        assertNotNull(resumeIdResponse);
        log.info("Resume found with resourceId: {}", resumeId);
        assertAndLogResponse(resumeIdResponse);
    }

    @Test
    @Order(4)
    public void createSpecificRecord() throws Exception {
        ResumeResponse response = getRecordByResumeId();
        int status;
        String content;
        MvcResult mvcResult;

        ResumeMapper mapper = new ResumeMapper();
        ResumeRequest request = mapper.map(response, ResumeRequest.class);
        assertAndLogResponse(request);
        request.setId(UUID.fromString(idCreatedDeleted));

        setChildTables(request);

        String json = super.mapToJson(request);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        status = mvcResult.getResponse().getStatus();

        assertEquals(201, status);
        content = mvcResult.getResponse().getContentAsString();

        assertAndLogResponse(content);

        ResumeIdResponse resumeIdResponse = super.mapFromJson(content, ResumeIdResponse.class);
        assertNotNull(resumeIdResponse);
        log.info("Resume found with resourceId: {}", idCreatedDeleted);
        assertAndLogResponse(resumeIdResponse);
    }

    @Test
    @Order(5)
    public void deleteDummyRecord() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("resumeId", idCreatedDeleted)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        assertTrue(content.contains("resumeId"));
        log.info("Entry deleted for: {}", content);
    }

    @Test
    @Order(6)
    public void updateRecord() throws Exception {
        ResumeResponse response = getRecordByResumeId();
        int status;
        String content;
        MvcResult mvcResult;

        ResumeMapper mapper = new ResumeMapper();
        ResumeRequest request = mapper.map(response, ResumeRequest.class);
        assertAndLogResponse(request);
        request.setId(null);
        request.setCreationDate(LocalDate.now());

        setChildTables(request);

        String json = super.mapToJson(request);

        mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);
        content = mvcResult.getResponse().getContentAsString();

        assertAndLogResponse(content);

        ResumeIdResponse resumeIdResponse = super.mapFromJson(content, ResumeIdResponse.class);
        assertNotNull(resumeIdResponse);
        log.info("Resume found with resourceId: {}", resumeId);
        assertAndLogResponse(resumeIdResponse);
    }

    @Test
    @Order(7)
    public void setOriginal() throws Exception {
        ResumeRequest request = super.mapFromJson(initialResume, ResumeRequest.class);
        assertAndLogResponse(request);

        List<ResumeResponse> records = getRecords();
        MvcResult mvcResult;
        int status;
        String content;

        if (records.size() > 0)
            for (ResumeResponse record :
                    records) {
                mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("resumeId", String.valueOf(record.getId()))
                        .accept(MediaType.APPLICATION_JSON)).andReturn();

                status = mvcResult.getResponse().getStatus();
                assertEquals(200, status);
                content = mvcResult.getResponse().getContentAsString();
                assertAndLogResponse(content);
                assertTrue(content.contains("resumeId"));
                log.info("Entry deleted for: {}", content);
            }

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
    @Order(8)
    public void getByResumeIdAndThrowNotRecordFound() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        String expectedMessage = "Not Found";
        assertTrue(content.contains(expectedMessage));
    }

    @Test
    @Order(9)
    public void deleteDummyRecordAndThrowNotRecordFound() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("resumeId", String.valueOf(UUID.randomUUID()))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertAndLogResponse(content);
        String expectedMessage = "Not Found";
        assertTrue(content.contains(expectedMessage));
    }

    @Test
    @Order(10)
    public void testValidator() throws Exception {
        ResumeResponse response = getRecordByResumeId();
        int status;
        String content;
        MvcResult mvcResult;

        ResumeMapper mapper = new ResumeMapper();
        ResumeRequest request = mapper.map(response, ResumeRequest.class);
        assertAndLogResponse(request);
        request.setId(UUID.fromString(idCreatedDeleted));
        request.setPhone("3121557091");

        setChildTables(request);

        request.getSkills().get(0).setPercentage(150);

        request.getWorkExperiences().get(0).setCompany(null);
        request.getWorkExperiences().get(0).setStartDate(null);
        request.getWorkExperiences().get(0).setCurrentJob(false);
        request.getWorkExperiences().get(0).setEndDate(null);

        request.getSchools().get(0).setCareer(null);
        request.getSchools().get(0).setDegree(null);
        request.getSchools().get(0).setStartDate(null);
        request.getSchools().get(0).setEndDate(null);

        String json = super.mapToJson(request);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        status = mvcResult.getResponse().getStatus();

        assertEquals(400, status);
        content = mvcResult.getResponse().getContentAsString();

        assertAndLogResponse(content);

        String expectedMessage = "error";
        assertTrue(content.contains(expectedMessage));
    }

    @Test
    @Order(11)
    public void testValidatorSpecific() throws Exception {
        ResumeResponse response = getRecordByResumeId();
        int status;
        String content;
        MvcResult mvcResult;

        ResumeMapper mapper = new ResumeMapper();
        ResumeRequest request = mapper.map(response, ResumeRequest.class);
        assertAndLogResponse(request);
        request.setId(UUID.fromString(idCreatedDeleted));
        request.setPhone("3121557091");

        setChildTables(request);

        request.getSkills().get(0).setPercentage(150);

        request.getWorkExperiences().get(0).setCompany(null);
        request.getWorkExperiences().get(0).setStartDate(LocalDate.of(2023, 4, 26));
        request.getWorkExperiences().get(0).setCurrentJob(false);
        request.getWorkExperiences().get(0).setEndDate(LocalDate.of(2023, 4, 25));

        request.getSchools().get(0).setCareer(null);
        request.getSchools().get(0).setDegree("null");
        request.getSchools().get(0).setStartDate(LocalDate.of(2023, 4, 26));
        request.getSchools().get(0).setEndDate(LocalDate.of(2023, 4, 25));

        String json = super.mapToJson(request);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        status = mvcResult.getResponse().getStatus();

        assertEquals(400, status);
        content = mvcResult.getResponse().getContentAsString();

        assertAndLogResponse(content);

        String expectedMessage = "error";
        assertTrue(content.contains(expectedMessage));
    }

}