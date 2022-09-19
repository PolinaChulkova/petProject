package com.example.petproject.repository;

import com.example.petproject.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    Optional<News> findNewsById(long id);
    Optional<News> findByNewsName(String newsName);
    boolean existsById(long id);
    Optional<News> deleteById(long id);
    List<News> findAll();
}
