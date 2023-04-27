package com.izars.resumeapi.education.service;

import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.education.domain.EducationRequest;
import com.izars.resumeapi.education.domain.EducationResponse;

import java.util.UUID;

public interface EducationService {
    EducationResponse getEducationbyResourceId(UUID resumeId);

    EducationResponse saveEducation(EducationRequest request);

    GenericDeleteResponse deleteEducationbyResumeId(UUID resumeId);

    GenericDeleteResponse deleteEducationbyId(UUID resumeId, UUID id);
}
