package com.izars.resumeapi.auth.validator;

import com.izars.resumeapi.auth.domain.jwt.JwtRequest;
import com.izars.resumeapi.auth.exception.CustomBadRequestException;
import com.izars.resumeapi.auth.exception.ExceptionBody;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.izars.resumeapi.auth.validator.CustomValidationUtils.ERROR_MESSAGE;
import static com.izars.resumeapi.auth.validator.CustomValidationUtils.validateRequiredString;

@Service
public class JwtRequestValidator implements CustomValidator<JwtRequest> {
    @Override
    public void validate(JwtRequest request) {
        List<ExceptionBody.ErrorDetails> errorDetails = new ArrayList<>();

        validateRequiredString(errorDetails, request.getUsername(), "username");
        validateRequiredString(errorDetails, request.getPassword(), "password");

        if (!errorDetails.isEmpty()) throw new CustomBadRequestException(errorDetails, ERROR_MESSAGE);
    }
}
