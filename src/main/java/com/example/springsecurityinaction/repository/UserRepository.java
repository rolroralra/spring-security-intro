package com.example.springsecurityinaction.repository;

import com.example.springsecurityinaction.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
