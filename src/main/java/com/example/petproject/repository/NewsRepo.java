package com.example.petproject.repository;

import com.example.petproject.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    Optional<News> findNewsById(long id);
    Optional<News> findByNewsName(String newsName);
    boolean existsById(long id);
    Optional<News> deleteById(long id);
    Page<News> findAll(Pageable pageable);
    @Query(value = "SELECT n FROM News n WHERE CONCAT(n.newsName, n.text) LIKE %?1%")
    Page<News> searchByKey(String key, Pageable pageable);
}
