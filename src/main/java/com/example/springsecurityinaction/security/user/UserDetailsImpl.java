package com.example.springsecurityinaction.security.user;

import com.example.springsecurityinaction.domain.EncryptionAlgorithm;
import com.example.springsecurityinaction.domain.User;
import com.example.springsecurityinaction.domain.UserAuthority;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getUserAuthorities().stream()
            .map(UserAuthority::grantedAuthority)
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public EncryptionAlgorithm getAlgorithm() {
        return user.getAlgorithm();
    }
}
