package com.example.petproject.repository;

import com.example.petproject.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {

    Optional<News> findNewsById(long id);

    boolean existsByNewsNameAndPublicationDate(String newsName, LocalDateTime publicationDate);

    Page<News> findAll(Pageable pageable);

    @Transactional
    @Query(value = "SELECT n FROM News n WHERE CONCAT(n.newsName, n.text, n.publicationDate) LIKE %||LOWER(TRIM(:key))||%")
    Page<News> searchByKey(@Param("key") String key, Pageable pageable);

