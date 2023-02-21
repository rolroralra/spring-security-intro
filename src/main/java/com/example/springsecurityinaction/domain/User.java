package com.example.springsecurityinaction.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_INFO")
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "USER_AUTHORITY", joinColumns = @JoinColumn(name = "USER_ID"))
    private Set<UserAuthority> userAuthorities = new HashSet<>();

    public void addAuthorities(UserAuthority... authorities) {
        userAuthorities.addAll(List.of(authorities));
    }
}
