package com.example.springsecurityinaction.repository;


import com.example.springsecurityinaction.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

class UserRepositoryTest extends JpaRepositoryTest<User, Long> {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> repository() {
        return userRepository;
    }

    @Override
    protected User createTestInstance() {
        return new User("test", "test");
    }
}
