package com.izars.resumeapi.experience.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.configuration.util.DummyGenericTestData;
import com.izars.resumeapi.experience.domain.ExperienceRequest;
import com.izars.resumeapi.experience.domain.ExperienceResponse;
import com.izars.resumeapi.experience.service.ExperienceService;
import com.izars.resumeapi.experience.service.ExperienceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyGenericDeleteData;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.generateRandomIdString;
import static com.izars.resumeapi.experience.DummyTestExperienceData.dummyExperienceRequestData;
import static com.izars.resumeapi.experience.DummyTestExperienceData.dummyExperienceResponseData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
class ExperienceControllerTest {
    private static final UUID resumeId = DummyGenericTestData.generateRandomIdString();
    private static final UUID randomId = UUID.randomUUID();
    private static ExperienceController controller;

    @BeforeEach
    public void init() {
        ExperienceResponse response = dummyExperienceResponseData();
        ExperienceService service = Mockito.mock(ExperienceServiceImpl.class);
        Mockito.doThrow(new CustomNotFoundException(String.format("No Record was found for resumeId %s", randomId)))
                .when(service).getExperiencebyResourceId(randomId);
        Mockito.doReturn(response)
                .when(service).getExperiencebyResourceId(resumeId);
        controller = new ExperienceController(service);
        Mockito.doReturn(response)
                .when(service).saveExperience(any());
        GenericDeleteResponse genericDeleteResponse = dummyGenericDeleteData();
        Mockito.doThrow(new CustomNotFoundException(String.format("No Record was found for resumeId %s", randomId)))
                .when(service).deleteExperiencebyResumeId(randomId);
        Mockito.doReturn(genericDeleteResponse)
                .when(service).deleteExperiencebyResumeId(resumeId);
        Mockito.doReturn(genericDeleteResponse)
                .when(service).deleteExperiencebyId(any(UUID.class), any(UUID.class));
        ReflectionTestUtils.setField(controller, "service", service);
    }

    @Test
    public void givenValidResumeId_whenGetListbyId_thenSuccess() throws JsonProcessingException {
        ExperienceResponse response = controller.getExperienceListbyId(String.valueOf(resumeId));
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidResumeId_whenGetListbyId_thenException() {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            ExperienceResponse response = controller.getExperienceListbyId(String.valueOf(randomId));
        });

        String expectedMessage = "No Record was found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenValidRequest_whenCreateList_thenSuccess() throws JsonProcessingException {
        ExperienceRequest request = dummyExperienceRequestData();
        ExperienceResponse response = controller.createExperienceList(request);
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidResumeId_whenDeleteList_thenSuccess() {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            GenericDeleteResponse response = controller.deleteExperienceList(String.valueOf(randomId), null);
        });

        String expectedMessage = "No Record was found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenValidResumeId_whenDeleteList_thenSuccess() throws JsonProcessingException {
        GenericDeleteResponse response = controller.deleteExperienceList(String.valueOf(resumeId), null);
        assertAndLogResponse(response);
    }

    @Test
    public void givenValidResumeIdandId_whenDeleteList_thenSuccess() throws JsonProcessingException {
        GenericDeleteResponse response = controller.deleteExperienceList(String.valueOf(resumeId), String.valueOf(generateRandomIdString()));
        assertAndLogResponse(response);
    }
}