package com.example.springsecurityinaction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home.html";
    }

    @GetMapping("/main")
    public String main() {
        return "redirect:/products";
    }
}
