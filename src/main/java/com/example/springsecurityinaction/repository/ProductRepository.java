package com.example.springsecurityinaction.repository;

import com.example.springsecurityinaction.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
