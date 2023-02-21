package com.example.springsecurityinaction.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("rolroralra")
//            .password("password")
//            .authorities("read", "write", "delete")
//        .and()
//            .passwordEncoder(NoOpPasswordEncoder.getInstance());

        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeHttpRequests()
            .anyRequest().authenticated();
    }
}
