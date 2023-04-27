package com.izars.resumeapi.skills.service;

import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.auth.model.Skill;
import com.izars.resumeapi.auth.model.Skill.SkillId;
import com.izars.resumeapi.auth.repository.ResumeRepository;
import com.izars.resumeapi.skills.domain.SkillDomain;
import com.izars.resumeapi.skills.domain.SkillRequest;
import com.izars.resumeapi.skills.domain.SkillResponse;
import com.izars.resumeapi.skills.mapper.SkillMapper;
import com.izars.resumeapi.skills.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.izars.resumeapi.auth.utils.SpringUtils.getRandomId;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repo;

    private final ResumeRepository resumeRepo;

    private final SkillMapper mapper;

    public SkillServiceImpl(SkillRepository repo, ResumeRepository resumeRepo, SkillMapper mapper) {
        this.repo = repo;
        this.resumeRepo = resumeRepo;
        this.mapper = mapper;
    }

    @Override
    public SkillResponse getSkillsbyResourceId(UUID resumeId) {
        List<Skill> list = repo.findByIdResumeId(resumeId);

        if (list.isEmpty())
            throw new CustomNotFoundException(String.format("No Record was found for resumeId %s", resumeId));

        return new SkillResponse(mapper.mapAsList(list, SkillDomain.class));
    }

    @Override
    public SkillResponse saveSkills(SkillRequest request) {
        Skill.SkillId id = new Skill.SkillId(request.getSkillList().get(0).getId()
                , request.getSkillList().get(0).getResumeId());

        UUID resumeId = checkResumeId(id);

        for (SkillDomain row : request.getSkillList()) {
            row.setResumeId(resumeId);
            if (row.getId() == null) row.setId(getRandomId());
        }

        List<Skill> response = mapper.mapAsList(request.getSkillList(), Skill.class);

        repo.saveAll(response);

        return getSkillsbyResourceId(resumeId);
    }

    private UUID checkResumeId(SkillId id) {
        return resumeRepo.findById(id.getResumeId())
                .orElseThrow(() -> new CustomNotFoundException(String.format("Resume with id %s was not found", id.getResumeId().toString())))
                .getId();
    }

    @Override
    public GenericDeleteResponse deleteSkillsbyResumeId(UUID resumeId) {
        List<Skill> list = repo.findByIdResumeId(resumeId);

        if (list.isEmpty())
            throw new CustomNotFoundException(String.format("No Skill record was found for resumeId %s", resumeId));

        repo.deleteAll(list);

        return new GenericDeleteResponse(resumeId);
    }

    @Override
    public GenericDeleteResponse deleteSkillsbyId(UUID resumeId, UUID id) {
        SkillId rowId = new SkillId(id, resumeId);
        checkResumeId(rowId);

        Skill row = repo.findById(rowId)
                .orElseThrow(() -> new CustomNotFoundException(String.format("Skill with id %s was not found for ResumeId %s", rowId.getId(), rowId.getResumeId())));

        repo.delete(row);

        return new GenericDeleteResponse(id, resumeId);
    }
}
