package com.izars.resumeapi.skills.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillDomain {
    private UUID id;
    private UUID resumeId;
    private String name;
    private int percentage;
    private String type;
}
