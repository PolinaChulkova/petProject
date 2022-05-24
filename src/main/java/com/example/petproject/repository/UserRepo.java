package com.example.petproject.repository;

import com.example.petproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findUserById(long id);
    User findByUsername(String username);
    boolean existsById(long id);
    User deleteById(long id);
    User save(User user);
    List<User> findAll();
    User findUserByEmail(String email);
}
