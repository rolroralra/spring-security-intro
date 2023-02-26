package com.example.springsecurityinaction.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserAuthority {
    READ,
    WRITE,
    DELETE,

    ROLE_MANAGER,

    ROLE_ADMIN;

    public GrantedAuthority grantedAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }

    public static List<GrantedAuthority> allGrantedAuthorities() {
        return Arrays.stream(values())
            .map(UserAuthority::grantedAuthority)
            .collect(Collectors.toList());
    }
}
