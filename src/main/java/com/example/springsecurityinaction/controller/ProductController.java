package com.example.springsecurityinaction.controller;

import com.example.springsecurityinaction.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String productPage(Authentication auth, Model model) {
        model.addAttribute("username", auth.getName());
        model.addAttribute("products", productService.findAll());
        return "main";
    }

    @ResponseBody
    @GetMapping("/{code}")
    public String productCode(@PathVariable String code) {
        return code;
    }

    @PostMapping("/add")
    public String add(@RequestParam String name) {
        log.info("Adding product {}", name);

        return "redirect:/products";
    }
}
