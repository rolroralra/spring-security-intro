package com.example.springsecurityinaction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CorsController {

    @CrossOrigin("http://localhost:8080")
    @PostMapping("/cors")
    public String cors() {
        log.info("POST /cors method called");
        return "HELLO";
    }
}
