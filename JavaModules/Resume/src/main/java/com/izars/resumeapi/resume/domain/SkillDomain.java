package com.izars.resumeapi.resume.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkillDomain {
    private UUID id;
    private UUID resumeId;
    @NotBlank
    private String name;
    @NotBlank
    private int percentage;
    @NotBlank
    private String type;
}
