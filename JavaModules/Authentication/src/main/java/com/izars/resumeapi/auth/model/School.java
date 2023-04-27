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
public class School {
    @EmbeddedId
    private SchoolId id;

    private String name;
    private String career;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "resume_id", insertable = false, updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private Resume resume;

    @PreRemove
    public void preRemoveSchool() {
        resume.getSchools().remove(this);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SchoolId implements Serializable {
        @Column(name = "id")
        private UUID id;
        @Column(name = "resume_id")
        private UUID resumeId;
    }
}
