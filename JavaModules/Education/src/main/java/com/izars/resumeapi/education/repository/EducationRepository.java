package com.izars.resumeapi.education.repository;

import com.izars.resumeapi.auth.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EducationRepository extends JpaRepository<School, School.SchoolId> {
    List<School> findByIdResumeId(UUID id);
}
