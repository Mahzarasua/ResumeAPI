package com.izars.resumeapi.experience.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceRequest {
    private List<ExperienceDomain> experienceList;
}
