package com.izars.resumeapi.resume.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExperienceDomain {
    private UUID id;
    private UUID resumeId;
    private String title;
    private String company;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean currentJob;
    private String description;
}
