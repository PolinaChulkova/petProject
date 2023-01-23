package com.example.petproject.repository;

import com.example.petproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> deleteByUsername(String username);

    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email LIKE %?1%")
    Page<User> searchUserByEmail(String email, Pageable pageable);
}
