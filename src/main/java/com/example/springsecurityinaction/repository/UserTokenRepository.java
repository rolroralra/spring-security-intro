package com.example.springsecurityinaction.repository;

import com.example.springsecurityinaction.domain.UserToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByIdentifier(String identifier);
}
