package com.izars.resumeapi.resume.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.configuration.util.DummyGenericTestData;
import com.izars.resumeapi.resume.domain.ResumeIdResponse;
import com.izars.resumeapi.resume.domain.ResumeRequest;
import com.izars.resumeapi.resume.domain.ResumeResponse;
import com.izars.resumeapi.resume.service.ResumeService;
import com.izars.resumeapi.resume.service.ResumeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.generateRandomIdString;
import static com.izars.resumeapi.resume.DummyTestResumeData.dummyResumeRequestData;
import static com.izars.resumeapi.resume.DummyTestResumeData.dummyResumeResponseData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
class ResumeControllerTest {
    private static final UUID resumeId = DummyGenericTestData.generateRandomIdString();
    private static final UUID randomId = UUID.randomUUID();
    private static final ResumeRequest request = dummyResumeRequestData();
    private static ResumeController controller;

    @BeforeEach
    public void init() {
        ResumeResponse response = dummyResumeResponseData();
        ResumeIdResponse resumeIdResponse = new ResumeIdResponse(generateRandomIdString());
        ResumeService service = Mockito.mock(ResumeServiceImpl.class);
        Mockito.doThrow(new CustomNotFoundException(String.format("Resume with id %s was not found", randomId)))
                .when(service).getResumeById(randomId);
        Mockito.doReturn(response)
                .when(service).getResumeById(resumeId);
        Mockito.doReturn(Collections.singletonList(response))
                .when(service).getAllResumes();
        controller = new ResumeController(service);
        Mockito.doReturn(resumeIdResponse)
                .when(service).saveResume(any());
        Mockito.doReturn(resumeIdResponse)
                .when(service).saveResume(any(), any(UUID.class));
        Mockito.doThrow(new CustomNotFoundException(String.format("No Record was found for resumeId %s", randomId)))
                .when(service).saveResume(request, randomId);
        Mockito.doThrow(new CustomNotFoundException(String.format("No Record was found for resumeId %s", randomId)))
                .when(service).deleteResumeById(randomId);
        Mockito.doReturn(resumeIdResponse)
                .when(service).deleteResumeById(resumeId);
        ReflectionTestUtils.setField(controller, "service", service);
    }

    @Test
    public void getAllResumes() throws JsonProcessingException {
        List<ResumeResponse> response = controller.getResumes();
        assertAndLogResponse(response);
    }

    @Test
    public void givenValidResumeId_whenGetListbyId_thenSuccess() throws JsonProcessingException {
        ResumeResponse response = controller.getResumeById(String.valueOf(resumeId));
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidResumeId_whenGetListbyId_throwException() throws JsonProcessingException {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            ResumeResponse response = controller.getResumeById(String.valueOf(randomId));
        });

        String expectedMessage = "Record not Found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));
        assertAndLogResponse(exception.getErrorDetails());
    }

    @Test
    public void givenValidRequest_whenCreateList_thenSuccess() throws JsonProcessingException {
        ResumeIdResponse response = controller.createResume(request);
        assertAndLogResponse(response);
    }

    @Test
    public void givenValidRequest_whenUpdateList_thenSuccess() throws JsonProcessingException {
        ResumeIdResponse response = controller.updateResume(String.valueOf(resumeId), request);
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidRequest_whenUpdateList_throwException() throws JsonProcessingException {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            ResumeIdResponse response = controller.updateResume(String.valueOf(randomId), request);
        });
        String expectedMessage = "Record not Found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));
        assertAndLogResponse(exception.getErrorDetails());
    }

    @Test
    public void givenValidResumeId_whenDeleteList_thenSuccess() throws JsonProcessingException {
        ResumeIdResponse response = controller.deleteResumeById(String.valueOf(resumeId));
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidRequest_whenDeleteList_throwException() throws JsonProcessingException {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            ResumeIdResponse response = controller.deleteResumeById(String.valueOf(randomId));
        });
        String expectedMessage = "Record not Found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));
        assertAndLogResponse(exception.getErrorDetails());
    }

}