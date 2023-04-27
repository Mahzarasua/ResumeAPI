package com.izars.resumeapi.experience.service;

import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.experience.domain.ExperienceRequest;
import com.izars.resumeapi.experience.domain.ExperienceResponse;

import java.util.UUID;

public interface ExperienceService {
    ExperienceResponse getExperiencebyResourceId(UUID resumeId);

    ExperienceResponse saveExperience(ExperienceRequest request);

    GenericDeleteResponse deleteExperiencebyResumeId(UUID resumeId);

    GenericDeleteResponse deleteExperiencebyId(UUID resumeId, UUID id);
}
