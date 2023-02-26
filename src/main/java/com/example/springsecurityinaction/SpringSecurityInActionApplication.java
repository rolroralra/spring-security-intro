package com.example.springsecurityinaction;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityInActionApplication /*implements InitializingBean*/ {
//    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityInActionApplication.class, args);
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        User user = new User("admin", "password");
//        user.addAuthorities(READ, WRITE, DELETE);
//        userRepository.save(user);
//    }
}
