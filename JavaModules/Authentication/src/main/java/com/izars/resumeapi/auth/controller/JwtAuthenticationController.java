package com.izars.resumeapi.auth.controller;

import com.izars.resumeapi.auth.config.CustomAuthenticationManager;
import com.izars.resumeapi.auth.config.JwtTokenUtil;
import com.izars.resumeapi.auth.domain.jwt.JwtRequest;
import com.izars.resumeapi.auth.domain.jwt.JwtResponse;
import com.izars.resumeapi.auth.exception.ExceptionBody;
import com.izars.resumeapi.auth.validator.JwtRequestValidator;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Validated
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized",
                content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema())}),
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ExceptionBody.class))}),
        @ApiResponse(responseCode = "404", description = "Not Found",
                content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ExceptionBody.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ExceptionBody.class))})
})
public class JwtAuthenticationController {


    private final CustomAuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtRequestValidator validator;

    public JwtAuthenticationController(CustomAuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtRequestValidator validator) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.validator = validator;
    }

    @PostMapping(value = "/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authRequest) {
        validator.validate(authRequest);
        return new JwtResponse(
                jwtTokenUtil.generateToken(authenticationManager.authentication(authRequest.getUsername(), authRequest.getPassword()))
        );
    }

}
