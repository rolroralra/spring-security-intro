package com.example.springsecurityinaction.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Product(String name, Integer price, Currency currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }
}
