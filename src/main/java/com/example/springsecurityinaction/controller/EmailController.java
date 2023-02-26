package com.example.springsecurityinaction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @GetMapping("/{email}")
    public String email(@PathVariable String email) {
        return "Email Address: " + email;
    }
}
