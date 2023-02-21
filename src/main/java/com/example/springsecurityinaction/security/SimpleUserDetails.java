package com.example.springsecurityinaction.security;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public class SimpleUserDetails extends AbstractUserDetails {
    private final String username;

    private final String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
            new SimpleGrantedAuthority("READ"),
            new SimpleGrantedAuthority("WRITE"),
            new SimpleGrantedAuthority("DELETE")
        );
    }

    @Override
    public String getPassword() {
        return username;
    }

    @Override
    public String getUsername() {
        return password;
    }
}
