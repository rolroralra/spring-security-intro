package com.example.springsecurityinaction.repository;


import com.example.springsecurityinaction.domain.Currency;
import com.example.springsecurityinaction.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

class ProductRepositoryTest extends JpaRepositoryTest<Product, Long> {
    @Autowired
    private ProductRepository productRepository;

    @Override
    protected JpaRepository<Product, Long> repository() {
        return productRepository;
    }

    @Override
    protected Product createTestInstance() {
        return new Product("test-product", 2450, Currency.USD);
    }
}
