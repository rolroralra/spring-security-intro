package com.example.springsecurityinaction.security.auth;

import com.example.springsecurityinaction.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final JpaUserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);

        return checkPassword(userDetails, password);
    }

    private Authentication checkPassword(UserDetailsImpl userDetails, String rawPassword) {
        switch (userDetails.getAlgorithm()) {
            case BCRYPT:
                return checkPassword(userDetails, rawPassword, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(userDetails, rawPassword, sCryptPasswordEncoder);
            default:
                throw new BadCredentialsException("Not Supported Algorithm");
        }
    }

    private Authentication checkPassword(UserDetails userDetails, String rawPassword, PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
