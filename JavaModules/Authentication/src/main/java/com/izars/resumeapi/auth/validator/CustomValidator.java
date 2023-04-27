package com.izars.resumeapi.auth.validator;

public interface CustomValidator<E> {
    void validate(E request);
}
