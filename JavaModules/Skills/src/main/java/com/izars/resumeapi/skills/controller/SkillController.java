package com.izars.resumeapi.skills.controller;

import com.izars.resumeapi.auth.domain.GenericDeleteResponse;
import com.izars.resumeapi.auth.exception.ExceptionBody;
import com.izars.resumeapi.skills.domain.SkillRequest;
import com.izars.resumeapi.skills.domain.SkillResponse;
import com.izars.resumeapi.skills.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.izars.resumeapi.auth.utils.SpringUtils.getUuid;

@Validated
@RestController
@RequestMapping(value = "/api/v1/skill", produces = MediaType.APPLICATION_JSON_VALUE)
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
public class SkillController {

    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    @GetMapping(value = "/{resumeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This operation will return a list of schools associated to a resume id")
    public SkillResponse getSkillListbyId(@PathVariable String resumeId) {
        return service.getSkillsbyResourceId(getUuid(resumeId));
    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This operation will associate a new school and will return the list of schools associated to a resume id")
    public SkillResponse createSkillList(@RequestBody SkillRequest request) {
        return service.saveSkills(request);
    }

    @DeleteMapping(value = "/{resumeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This operation will remove a skill record and will return the list of skills associated to a resume id")
    public GenericDeleteResponse deleteSkillList(@PathVariable String resumeId,
                                                 @RequestParam(required = false) String id) {
        return (id != null) ? service.deleteSkillsbyId(getUuid(resumeId), getUuid(id))
                : service.deleteSkillsbyResumeId(getUuid(resumeId));
    }
}