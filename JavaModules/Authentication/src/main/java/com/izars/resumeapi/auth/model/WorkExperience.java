package com.izars.resumeapi.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperience {
    @EmbeddedId
    private WorkExperienceId id;

    private String title;
    private String company;
    private boolean currentJob;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "resume_id", insertable = false, updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private Resume resume;

    @PreRemove
    public void preRemoveWorkExp() {
        resume.getWorkExperiences().remove(this);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkExperienceId implements Serializable {
        @Column(name = "id")
        private UUID id;
        @Column(name = "resume_id")
        private UUID resumeId;
    }
}
