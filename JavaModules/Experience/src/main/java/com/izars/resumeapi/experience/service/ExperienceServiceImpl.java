package com.izars.resumeapi.experience.service;

import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.auth.model.WorkExperience;
import com.izars.resumeapi.auth.model.WorkExperience.WorkExperienceId;
import com.izars.resumeapi.auth.repository.ResumeRepository;
import com.izars.resumeapi.experience.domain.ExperienceDomain;
import com.izars.resumeapi.experience.domain.ExperienceRequest;
import com.izars.resumeapi.experience.domain.ExperienceResponse;
import com.izars.resumeapi.experience.mapper.ExperienceMapper;
import com.izars.resumeapi.experience.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.izars.resumeapi.auth.utils.SpringUtils.getRandomId;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository repo;

    private final ResumeRepository resumeRepo;

    private final ExperienceMapper mapper;

    public ExperienceServiceImpl(ExperienceRepository repo, ResumeRepository resumeRepo, ExperienceMapper mapper) {
        this.repo = repo;
        this.resumeRepo = resumeRepo;
        this.mapper = mapper;
    }

    @Override
    public ExperienceResponse getExperiencebyResourceId(UUID resumeId) {
        List<WorkExperience> list = repo.findByIdResumeId(resumeId);

        if (list.isEmpty())
            throw new CustomNotFoundException(String.format("No Record was found for resumeId %s", resumeId));

        return new ExperienceResponse(mapper.mapAsList(list, ExperienceDomain.class));
    }

    @Override
    public ExperienceResponse saveExperience(ExperienceRequest request) {
        WorkExperienceId id = new WorkExperienceId(request.getExperienceList().get(0).getId()
                , request.getExperienceList().get(0).getResumeId());

        UUID resumeId = checkResumeId(id);

        for (ExperienceDomain row : request.getExperienceList()) {
            row.setResumeId(resumeId);
            if (row.getId() == null) row.setId(getRandomId());
        }

        List<WorkExperience> response = mapper.mapAsList(request.getExperienceList(), WorkExperience.class);
        repo.saveAll(response);

        return getExperiencebyResourceId(resumeId);
    }

    private UUID checkResumeId(WorkExperienceId id) {
        return resumeRepo.findById(id.getResumeId())
                .orElseThrow(() -> new CustomNotFoundException(String.format("Resume with id %s was not found", id.getResumeId().toString())))
                .getId();
    }

    @Override
    public GenericDeleteResponse deleteExperiencebyResumeId(UUID resumeId) {
        List<WorkExperience> list = repo.findByIdResumeId(resumeId);

        if (list.isEmpty())
            throw new CustomNotFoundException(String.format("No Education record was found for resumeId %s", resumeId));

        return new GenericDeleteResponse(resumeId);
    }

    @Override
    public GenericDeleteResponse deleteExperiencebyId(UUID resumeId, UUID id) {
        WorkExperienceId rowId = new WorkExperienceId(id, resumeId);
        checkResumeId(rowId);

        WorkExperience row = repo.findById(rowId)
                .orElseThrow(() -> new CustomNotFoundException(String.format("Education with id %s was not found for ResumeId %s", rowId.getId(), rowId.getResumeId())));

        repo.delete(row);

        return new GenericDeleteResponse(id, resumeId);
    }
}
