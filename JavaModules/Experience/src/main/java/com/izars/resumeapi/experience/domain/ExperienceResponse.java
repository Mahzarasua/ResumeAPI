package com.izars.resumeapi.experience.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceResponse {
    private List<ExperienceDomain> experienceList;
}
