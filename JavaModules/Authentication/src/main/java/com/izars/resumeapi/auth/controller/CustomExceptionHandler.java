package com.izars.resumeapi.auth.controller;

import com.izars.resumeapi.auth.exception.CustomAuthException;
import com.izars.resumeapi.auth.exception.CustomBadRequestException;
import com.izars.resumeapi.auth.exception.CustomNotFoundException;
import com.izars.resumeapi.auth.exception.ExceptionBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    protected static final String URI_LABEL = "uri=";

    @ExceptionHandler(value = {CustomNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ResponseEntity<Object> handleNotFound(
            CustomNotFoundException exception, WebRequest request) {
        ExceptionBody body = new ExceptionBody();
        body.setTimestamp(LocalDateTime.now());
        body.setStatusCode(HttpStatus.NOT_FOUND.value());
        body.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        body.setMessage(exception.getMessage());
        body.setPath(request.getDescription(false).replace(URI_LABEL, ""));
        body.setDetails(exception.getErrorDetails());
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {CustomBadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> handleMissingRequiredFields(CustomBadRequestException exception, WebRequest request) {
        ExceptionBody body = new ExceptionBody();
        body.setTimestamp(LocalDateTime.now());
        body.setStatusCode(HttpStatus.BAD_REQUEST.value());
        body.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.setMessage(exception.getMessage());
        body.setPath(request.getDescription(false).replace(URI_LABEL, ""));
        body.setDetails(exception.getErrorDetails());
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {CustomAuthException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ResponseEntity<Object> handleAuthException(CustomAuthException exception, WebRequest request) {
        ExceptionBody body = new ExceptionBody();
        body.setTimestamp(LocalDateTime.now());
        body.setStatusCode(HttpStatus.BAD_REQUEST.value());
        body.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.setMessage(exception.getMessage());
        body.setPath(request.getDescription(false).replace(URI_LABEL, ""));
        body.setDetails(exception.getErrorDetails());
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
