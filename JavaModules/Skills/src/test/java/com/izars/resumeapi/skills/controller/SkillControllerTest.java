package com.izars.resumeapi.skills.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.configuration.util.DummyGenericTestData;
import com.izars.resumeapi.skills.domain.SkillRequest;
import com.izars.resumeapi.skills.domain.SkillResponse;
import com.izars.resumeapi.skills.service.SkillService;
import com.izars.resumeapi.skills.service.SkillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static com.izars.resumeapi.configuration.util.DummyGenericTestData.assertAndLogResponse;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.dummyGenericDeleteData;
import static com.izars.resumeapi.configuration.util.DummyGenericTestData.generateRandomIdString;
import static com.izars.resumeapi.skills.DummyTestSkillsData.dummySkillRequestData;
import static com.izars.resumeapi.skills.DummyTestSkillsData.dummySkillResponseData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
class SkillControllerTest {
    private static final UUID resumeId = DummyGenericTestData.generateRandomIdString();
    private static final UUID randomId = UUID.randomUUID();
    private static SkillController controller;

    @BeforeEach
    public void init() {
        SkillResponse response = dummySkillResponseData();
        SkillService service = Mockito.mock(SkillServiceImpl.class);
        Mockito.doThrow(new CustomNotFoundException(String.format("No Record was found for resumeId %s", randomId)))
                .when(service).getSkillsbyResourceId(randomId);
        Mockito.doReturn(response)
                .when(service).getSkillsbyResourceId(resumeId);
        controller = new SkillController(service);
        Mockito.doReturn(response)
                .when(service).saveSkills(any());
        GenericDeleteResponse genericDeleteResponse = dummyGenericDeleteData();
        Mockito.doThrow(new CustomNotFoundException(String.format("No Record was found for resumeId %s", randomId)))
                .when(service).deleteSkillsbyResumeId(randomId);
        Mockito.doReturn(genericDeleteResponse)
                .when(service).deleteSkillsbyResumeId(resumeId);
        Mockito.doReturn(genericDeleteResponse)
                .when(service).deleteSkillsbyId(any(UUID.class), any(UUID.class));
        ReflectionTestUtils.setField(controller, "service", service);
    }

    @Test
    public void givenValidResumeId_whenGetListbyId_thenSuccess() throws JsonProcessingException {
        SkillResponse response = controller.getSkillListbyId(String.valueOf(resumeId));
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidResumeId_whenGetListbyId_thenException() {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            SkillResponse response = controller.getSkillListbyId(String.valueOf(randomId));
        });

        String expectedMessage = "No Record was found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenValidRequest_whenCreateList_thenSuccess() throws JsonProcessingException {
        SkillRequest request = dummySkillRequestData();
        SkillResponse response = controller.createSkillList(request);
        assertAndLogResponse(response);
    }

    @Test
    public void givenInvalidResumeId_whenDeleteList_thenSuccess() {
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            GenericDeleteResponse response = controller.deleteSkillList(String.valueOf(randomId), null);
        });

        String expectedMessage = "No Record was found";
        String actualMessage = exception.getErrorDetails().get(0).toString();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenValidResumeId_whenDeleteList_thenSuccess() throws JsonProcessingException {
        GenericDeleteResponse response = controller.deleteSkillList(String.valueOf(resumeId), null);
        assertAndLogResponse(response);
    }

    @Test
    public void givenValidResumeIdandId_whenDeleteList_thenSuccess() throws JsonProcessingException {
        GenericDeleteResponse response = controller.deleteSkillList(String.valueOf(resumeId), String.valueOf(generateRandomIdString()));
        assertAndLogResponse(response);
    }

}