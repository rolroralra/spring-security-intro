package com.example.springsecurityinaction.security.entrypoint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("message", "I am your father");

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        response.sendError(httpStatus.value(), httpStatus.getReasonPhrase());
    }
}
