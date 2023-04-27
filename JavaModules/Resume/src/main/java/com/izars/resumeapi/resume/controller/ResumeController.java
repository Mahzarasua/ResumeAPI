package com.izars.resumeapi.resume.controller;

import com.izars.resumeapi.auth.exception.ExceptionBody;
import com.izars.resumeapi.resume.domain.ResumeIdResponse;
import com.izars.resumeapi.resume.domain.ResumeRequest;
import com.izars.resumeapi.resume.domain.ResumeResponse;
import com.izars.resumeapi.resume.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.izars.resumeapi.auth.utils.SpringUtils.getUuid;

@Validated
@RestController
@RequestMapping(value = "/api/v1/resume", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "jwtAuth")
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
        @ApiResponse(responseCode = "409", description = "Conflict",
                content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ExceptionBody.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ExceptionBody.class))})
})
public class ResumeController {

    private final ResumeService service;

    public ResumeController(ResumeService service) {
        this.service = service;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This operation will return the list of Resumes stored in the Database")
    public List<ResumeResponse> getResumes() {
        return service.getAllResumes();
    }

    @GetMapping(value = "/{resumeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This operation will return a resume resource by a specific resumeId")
    public ResumeResponse getResumeById(@Parameter(name = "resumeId", required = true) @PathVariable String resumeId) {
        return service.getResumeById(getUuid(resumeId));
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This operation will create a new resume and return the unique identifier of the Resume created")
    public ResumeIdResponse createResume(@RequestBody ResumeRequest request) {
        return service.saveResume(request);
    }

    @PutMapping(value = "/{resumeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This operation will update a resume and return the unique identifier of the Resume updated")
    public ResumeIdResponse updateResume(@Parameter(name = "resumeId", required = true) @PathVariable String resumeId,
                                         @RequestBody ResumeRequest request) {
        return service.saveResume(request, getUuid(resumeId));
    }

    @DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(summary = "This operation will remove a resume"
            , parameters = {
            @Parameter(name = "resumeId", required = true, in = ParameterIn.QUERY)
    })
    public ResumeIdResponse deleteResumeById(String resumeId) {
        return service.deleteResumeById(getUuid(resumeId));
    }
}
