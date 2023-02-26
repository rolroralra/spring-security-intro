package com.example.springsecurityinaction.service;

import com.example.springsecurityinaction.domain.Product;
import com.example.springsecurityinaction.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
