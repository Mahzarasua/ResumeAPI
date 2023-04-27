package com.izars.resumeapi.experience.repository;

import com.izars.resumeapi.auth.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExperienceRepository extends JpaRepository<WorkExperience, WorkExperience.WorkExperienceId> {
    List<WorkExperience> findByIdResumeId(UUID id);
}
