package com.example.springsecurityinaction.config;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableAsync
@Configuration
public class AsyncConfig {

//    @Bean
//    public InitializingBean initializingBean() {
//        return () ->
//            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }

    @PostConstruct
    void postConstruct() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
