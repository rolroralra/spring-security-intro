package com.example.springsecurityinaction.repository;


import com.example.springsecurityinaction.domain.UserToken;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

class UserTokenRepositoryTest extends JpaRepositoryTest<UserToken, Long> {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Override
    protected JpaRepository<UserToken, Long> repository() {
        return userTokenRepository;
    }

    @Override
    protected UserToken createTestInstance() {
        return new UserToken(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }
}
