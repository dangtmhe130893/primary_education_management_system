package com.primary_education_system.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String exceptionHandler() {
        return "access_denied";
    }
}
