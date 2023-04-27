package com.izars.resumeapi.resume.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolDomain {
    private UUID id;
    private UUID resumeId;
    private String name;
    private String career;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;
}
