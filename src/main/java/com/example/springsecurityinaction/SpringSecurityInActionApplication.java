package com.example.springsecurityinaction;

import static com.example.springsecurityinaction.domain.UserAuthority.*;

import com.example.springsecurityinaction.domain.User;
import com.example.springsecurityinaction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringSecurityInActionApplication implements InitializingBean {
    private final UserRepository  userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityInActionApplication.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        User user = new User("rolroralra", "password");
        user.addAuthorities(READ, WRITE, DELETE);
        userRepository.save(user);
    }
}
