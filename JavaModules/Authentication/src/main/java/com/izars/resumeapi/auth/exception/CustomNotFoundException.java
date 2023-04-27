package com.izars.resumeapi.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {

    private final List<ExceptionBody.ErrorDetails> errorDetails;

    public CustomNotFoundException(List<ExceptionBody.ErrorDetails> errorDetails, String errorMessage) {
        super(errorMessage);
        this.errorDetails = errorDetails;
    }

    public CustomNotFoundException(ExceptionBody.ErrorDetails errorDetail, String errorMessage) {
        super(errorMessage);
        this.errorDetails = Collections.singletonList(errorDetail);
    }

    public CustomNotFoundException(String errorMessage) {
        ExceptionBody.ErrorDetails detail = new ExceptionBody.ErrorDetails();
        detail.setErrorMessage(errorMessage);
        detail.setFieldName("Record not Found");
        this.errorDetails = Collections.singletonList(detail);
    }

    public List<ExceptionBody.ErrorDetails> getErrorDetails() {
        return errorDetails;
    }
}
