package com.izars.resumeapi.experience.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDomain {
    private UUID id;
    private UUID resumeId;
    @NotBlank
    private String title;
    @NotBlank
    private String company;
    private boolean currentJob;
    @NotBlank
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
