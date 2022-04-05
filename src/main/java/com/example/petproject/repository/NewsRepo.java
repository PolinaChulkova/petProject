package com.example.petproject.repository;

import com.example.petproject.model.news.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    News findNewsById(long id);
    News findByNewsName(String newsName);
    boolean existsById(long id);
    News deleteById(long id);
    News save(News news);
    List<News> findAll();
}
