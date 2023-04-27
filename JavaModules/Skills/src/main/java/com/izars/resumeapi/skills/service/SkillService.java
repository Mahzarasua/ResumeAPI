package com.izars.resumeapi.skills.service;

import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.skills.domain.SkillRequest;
import com.izars.resumeapi.skills.domain.SkillResponse;

import java.util.UUID;

public interface SkillService {

    SkillResponse getSkillsbyResourceId(UUID resumeId);

    SkillResponse saveSkills(SkillRequest request);

    GenericDeleteResponse deleteSkillsbyResumeId(UUID resumeId);

    GenericDeleteResponse deleteSkillsbyId(UUID resumeId, UUID id);
}
