package com.izars.resumeapi.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userrole {
    @EmbeddedId
    private UserroleId id;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserroleId implements Serializable {
        @Column(name = "user_id")
        private UUID userId;
        @Column(name = "role_id")
        private UUID roleId;
    }
}
