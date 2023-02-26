package com.example.springsecurityinaction.security.auth;

import com.example.springsecurityinaction.domain.User;
import com.example.springsecurityinaction.repository.UserRepository;
import com.example.springsecurityinaction.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));

        return new UserDetailsImpl(user);
    }
}
