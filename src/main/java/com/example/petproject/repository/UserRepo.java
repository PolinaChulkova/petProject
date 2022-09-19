package com.example.petproject.repository;

import com.example.petproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);
    Optional<User> findByUsername(String username);
    boolean existsById(long id);
    Optional<User> deleteByUsername(String username);
    List<User> findAll();
    Optional<User> findUserByEmail(String email);
}
