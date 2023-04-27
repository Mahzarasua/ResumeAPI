package com.izars.resumeapi.auth.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "DeleteResponse")
public class GenericDeleteResponse {
    private UUID id;
    private UUID resumeId;

    public GenericDeleteResponse(UUID resumeId) {
        this.resumeId = resumeId;
    }

    public GenericDeleteResponse(UUID id, UUID resumeId) {
        this.id = id;
        this.resumeId = resumeId;
    }
}
