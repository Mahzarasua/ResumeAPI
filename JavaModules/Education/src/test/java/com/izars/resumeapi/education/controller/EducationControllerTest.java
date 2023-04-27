package com.izars.resumeapi.education.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.configuration.util.DummyGenericTestData;
import com.izars.resumeapi.education.domain.EducationRequest;
import com.izars.resumeapi.education.domain.EducationResponse;
import com.izars.resumeapi.education.service.EducationService;
import com.izars.resumeapi.education.service.EducationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyGenericDeleteData;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.generateRandomIdString;
import static com.izars.resumeapi.education.DummyTestEducationData.dummyEducationRequestData;
import static com.izars.resumeapi.education.DummyTestEducationData.dummyEducationResponseData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
class EducationControllerTest {

    private static final UUID resumeId = DummyGenericTestData.generateRandomIdString();
    private static final UUID randomId = UUID.randomUUID();
    private static EducationController controller;

    @BeforeEach
    public void init() {
        EducationResponse response = dummyEducationResponseData();
        EducationService service = Mockito.mock(EducationServiceImpl.class);
        Mockito.doThrow(new CustomNotFoundException(String.format("No Record was found for resumeId %s", randomId)))
                .when(service).getEducationbyResourceId(randomId);
        Mockito.doReturn(response)
                .when(service).getEducationbyResourceId(resumeId);
        controller = new EducationController(service);
        Mockito.doReturn(response)
                .when(service).saveEducation(any());
        GenericDeleteResponse genericDeleteResponse = dummyGenericDeleteData();
        Mockito.doThrow(new CustomNotFoundException(String.format("No Record was found for resumeId %s", randomId)))
                .when(service).deleteEducationbyResumeId(randomId);
        Mockito.doReturn(genericDeleteResponse)
                .when(service).deleteEducationbyResumeId(resumeId);
        Mockito.doReturn(genericDeleteResponse)
                .when(service).deleteEducationbyId(any(UUID.class), any(UUID.class));
        ReflectionTestUtils.setField(controller, "service", service);
    }

    @Test
    public void givenValidResumeId_whenGetListbyId_thenSuccess() throws JsonProcessingException {
        EducationResponse response = controller.getEducationListbyId(String.valueOf(resumeId));
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidResumeId_whenGetListbyId_thenException() {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            EducationResponse response = controller.getEducationListbyId(String.valueOf(randomId));
        });

        String expectedMessage = "No Record was found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenValidRequest_whenCreateList_thenSuccess() throws JsonProcessingException {
        EducationRequest request = dummyEducationRequestData();
        EducationResponse response = controller.createEducationList(request);
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidResumeId_whenDeleteList_thenSuccess() {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            GenericDeleteResponse response = controller.deleteEducationList(String.valueOf(randomId), null);
        });

        String expectedMessage = "No Record was found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenValidResumeId_whenDeleteList_thenSuccess() throws JsonProcessingException {
        GenericDeleteResponse response = controller.deleteEducationList(String.valueOf(resumeId), null);
        assertAndLogResponse(response);
    }

    @Test
    public void givenValidResumeIdandId_whenDeleteList_thenSuccess() throws JsonProcessingException {
        GenericDeleteResponse response = controller.deleteEducationList(String.valueOf(resumeId), String.valueOf(generateRandomIdString()));
        assertAndLogResponse(response);
    }

}