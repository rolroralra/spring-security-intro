package com.example.springsecurityinaction.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(UnauthorizedException e) {
        log.error("Unauthorized exception", e);
        return HttpStatus.UNAUTHORIZED.getReasonPhrase();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(reason = "Internal Server Error")
    public String handleException(Exception e) {
        log.error("Internal Server Error", e);
        return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }
}
