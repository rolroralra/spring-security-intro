package com.example.springsecurityinaction.security.auth;

import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class InMemoryUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> userMap;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMap.entrySet().stream()
            .filter(e -> e.getKey().equals(username))
            .map(Entry::getValue)
            .findFirst()
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found")
            );
    }
}
