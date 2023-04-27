package com.izars.resumeapi.education.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "EducationRequest")
public class EducationRequest {
    private List<EducationDomain> educationList;
}
