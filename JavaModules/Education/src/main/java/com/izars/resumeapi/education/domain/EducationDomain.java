package com.izars.resumeapi.education.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationDomain {
    private UUID id;
    private UUID resumeId;
    @NotBlank
    private String name;
    @NotBlank
    private String career;
    @NotBlank
    private String degree;
    @NotBlank
    private LocalDate startDate;
    private LocalDate endDate;
}
