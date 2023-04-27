package com.izars.resumeapi.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExceptionBody {
    private LocalDateTime timestamp;
    private Integer statusCode;
    private String error;
    private String message;
    private String path;
    private List<ErrorDetails> details;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetails {
        private String fieldName;
        private String errorMessage;
    }
}
