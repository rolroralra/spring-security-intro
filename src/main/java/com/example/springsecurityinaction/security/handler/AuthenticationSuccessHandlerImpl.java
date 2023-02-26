package com.example.springsecurityinaction.security.handler;

import com.example.springsecurityinaction.domain.UserAuthority;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        var authorities = authentication.getAuthorities();

        var auth = authorities.stream()
            .filter(authority -> authority.getAuthority().equalsIgnoreCase(UserAuthority.READ.name()))
            .findFirst();

        if (auth.isPresent()) {
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/error");
        }
    }
}
