package com.izars.resumeapi.resume.service;

import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.auth.model.Resume;
import com.izars.resumeapi.auth.repository.ResumeRepository;
import com.izars.resumeapi.resume.domain.ExperienceDomain;
import com.izars.resumeapi.resume.domain.ResumeIdResponse;
import com.izars.resumeapi.resume.domain.ResumeRequest;
import com.izars.resumeapi.resume.domain.ResumeResponse;
import com.izars.resumeapi.resume.domain.SchoolDomain;
import com.izars.resumeapi.resume.domain.SkillDomain;
import com.izars.resumeapi.resume.mapper.ResumeMapper;
import com.izars.resumeapi.resume.validator.ResumeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.izars.resumeapi.auth.utils.SpringUtils.getRandomId;

@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository repo;

    private final ResumeMapper mapper;

    private final ResumeValidator validator;

    public ResumeServiceImpl(ResumeRepository repo, ResumeMapper mapper, ResumeValidator validator) {
        this.repo = repo;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<ResumeResponse> getAllResumes() {
        List<Resume> response = repo.findAll();
        return mapper.mapAsList(response, ResumeResponse.class);
    }

    @Override
    public ResumeResponse getResumeById(UUID id) {
        Resume resume = repo.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(String.format("Resume with id %s was not found", id)));
        return mapper.map(resume, ResumeResponse.class);
    }

    @Override
    public ResumeIdResponse saveResume(ResumeRequest request) {
        return saveResume(request, null);
    }

    @Override
    public ResumeIdResponse saveResume(ResumeRequest request, UUID id) {
        UUID resumeId = (id == null) ? (request.getId() == null) ? getRandomId() : request.getId()
                : mapper.map(getResumeById(id), Resume.class).getId();

        request.setId(resumeId);

        validateAndSaveResume(request);

        return new ResumeIdResponse(request.getId());
    }

    private void validateAndSaveResume(ResumeRequest request) {
        validator.validate(request);
        request.setCreationDate((request.getCreationDate() == null) ? LocalDate.now() : request.getCreationDate());

        setIdChildTables(request);

        Resume resume = mapper.map(request, Resume.class);
        repo.save(resume);
    }

    private void setIdChildTables(ResumeRequest request) {
        if (request.getSkills() != null)
            for (SkillDomain skill :
                    request.getSkills()) {
                if (skill.getId() == null) {
                    skill.setId(getRandomId());
                    skill.setResumeId(request.getId());
                }
            }

        if (request.getSchools() != null)
            for (SchoolDomain school :
                    request.getSchools()) {
                if (school.getId() == null) {
                    school.setId(getRandomId());
                    school.setResumeId(request.getId());
                }
            }

        if (request.getWorkExperiences() != null)
            for (ExperienceDomain workExperience :
                    request.getWorkExperiences()) {
                if (workExperience.getId() == null) {
                    workExperience.setId(getRandomId());
                    workExperience.setResumeId(request.getId());
                }
            }
    }

    @Override
    public ResumeIdResponse deleteResumeById(UUID id) {
        Resume resume = repo.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(String.format("No Record was found for resumeId %s", id)));

        ResumeRequest request = mapper.map(resume, ResumeRequest.class);

        removeChildRecords(request, id);
        repo.deleteById(id);

        return new ResumeIdResponse(id);
    }

    private void removeChildRecords(ResumeRequest request, UUID id) {
        request.setSchools(new ArrayList<>());
        request.setWorkExperiences(new ArrayList<>());
        request.setSkills(new ArrayList<>());

        saveResume(request, id);
    }
}
