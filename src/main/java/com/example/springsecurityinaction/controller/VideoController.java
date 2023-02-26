package com.example.springsecurityinaction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {
    @GetMapping("/{country}/{language}")
    public String video(@PathVariable String country, @PathVariable String language) {
        return "Video allowed for " + country + " " + language;
    }
}
