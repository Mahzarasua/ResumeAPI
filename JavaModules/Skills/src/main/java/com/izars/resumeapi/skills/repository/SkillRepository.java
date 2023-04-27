package com.izars.resumeapi.skills.repository;

import com.izars.resumeapi.auth.model.Skill;
import com.izars.resumeapi.auth.model.Skill.SkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SkillRepository extends JpaRepository<Skill, SkillId> {
    List<Skill> findByIdResumeId(UUID id);
}
