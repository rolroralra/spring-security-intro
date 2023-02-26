package com.example.springsecurityinaction.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_TOKEN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private String token;

    public UserToken(String identifier, String token) {
        this.identifier = identifier;
        this.token = token;
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
