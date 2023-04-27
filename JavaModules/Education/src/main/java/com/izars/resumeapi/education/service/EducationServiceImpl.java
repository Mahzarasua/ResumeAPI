package com.izars.resumeapi.education.service;

import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.auth.model.School;
import com.izars.resumeapi.auth.model.School.SchoolId;
import com.izars.resumeapi.auth.repository.ResumeRepository;
import com.izars.resumeapi.education.domain.EducationDomain;
import com.izars.resumeapi.education.domain.EducationRequest;
import com.izars.resumeapi.education.domain.EducationResponse;
import com.izars.resumeapi.education.mapper.EducationMapper;
import com.izars.resumeapi.education.repository.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.izars.resumeapi.auth.utils.SpringUtils.getRandomId;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository repo;

    private final ResumeRepository resumeRepo;

    private final EducationMapper mapper;

    public EducationServiceImpl(EducationRepository repo, ResumeRepository resumeRepo, EducationMapper mapper) {
        this.repo = repo;
        this.resumeRepo = resumeRepo;
        this.mapper = mapper;
    }

    @Override
    public EducationResponse getEducationbyResourceId(UUID resumeId) {
        List<School> list = repo.findByIdResumeId(resumeId);

        if (list.isEmpty())
            throw new CustomNotFoundException(String.format("No Record was found for resumeId %s", resumeId));

        return new EducationResponse(mapper.mapAsList(list, EducationDomain.class));
    }

    @Override
    public EducationResponse saveEducation(EducationRequest request) {
        SchoolId id = new SchoolId(request.getEducationList().get(0).getId(),
                request.getEducationList().get(0).getResumeId());

        UUID resumeId = checkResumeId(id);

        for (EducationDomain row : request.getEducationList()) {
            row.setResumeId(resumeId);
            if (row.getId() == null) row.setId(getRandomId());
        }

        List<School> response = mapper.mapAsList(request.getEducationList(), School.class);

        repo.saveAll(response);

        return getEducationbyResourceId(resumeId);
    }

    private UUID checkResumeId(SchoolId id) {
        return resumeRepo.findById(id.getResumeId())
                .orElseThrow(() -> new CustomNotFoundException(String.format("Resume with id %s was not found", id.getResumeId().toString())))
                .getId();
    }

    @Override
    public GenericDeleteResponse deleteEducationbyResumeId(UUID resumeId) {
        List<School> list = repo.findByIdResumeId(resumeId);

        if (list.isEmpty())
            throw new CustomNotFoundException(String.format("No Education record was found for resumeId %s", resumeId));

        repo.deleteAll(list);

        return new GenericDeleteResponse(resumeId);
    }

    @Override
    public GenericDeleteResponse deleteEducationbyId(UUID resumeId, UUID id) {
        SchoolId rowId = new SchoolId(id, resumeId);
        checkResumeId(rowId);

        School row = repo.findById(rowId)
                .orElseThrow(() -> new CustomNotFoundException(String.format("Education with id %s was not found for ResumeId %s", rowId.getId(), rowId.getResumeId())));
        repo.delete(row);

        return new GenericDeleteResponse(resumeId, id);
    }
}
