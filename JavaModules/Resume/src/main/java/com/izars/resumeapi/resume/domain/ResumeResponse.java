package com.izars.resumeapi.resume.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ResumeResponse")
public class ResumeResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String title;
    private String city;
    private String state;
    private String country;
    private String email;
    private String phone;
    private String summary;
    private LocalDate creationDate;
    private List<SkillDomain> skills;
    private List<SchoolDomain> schools;
    private List<ExperienceDomain> workExperiences;

}
