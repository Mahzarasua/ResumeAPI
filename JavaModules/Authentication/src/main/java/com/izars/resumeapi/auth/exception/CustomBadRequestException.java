package com.izars.resumeapi.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomBadRequestException extends RuntimeException {

    private final List<ExceptionBody.ErrorDetails> errorDetails;

    public CustomBadRequestException(List<ExceptionBody.ErrorDetails> errorDetails, String errorMessage) {
        super(errorMessage);
        this.errorDetails = errorDetails;
    }

    public CustomBadRequestException(ExceptionBody.ErrorDetails errorDetail, String errorMessage) {
        super(errorMessage);
        this.errorDetails = Collections.singletonList(errorDetail);
    }

    public List<ExceptionBody.ErrorDetails> getErrorDetails() {
        return errorDetails;
    }
}
