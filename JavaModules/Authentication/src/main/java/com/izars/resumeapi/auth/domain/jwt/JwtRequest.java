package com.izars.resumeapi.auth.domain.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class JwtRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1292422504791700471L;

    private String username;
    private String password;
}
