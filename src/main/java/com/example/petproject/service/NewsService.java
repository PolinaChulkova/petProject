package com.example.petproject.service;

import com.example.petproject.model.News;
import com.example.petproject.repository.NewsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsService {
    private final NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public void saveNews(News news) {
        try {
            newsRepo.save(news);

        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage() + ": Новость не сохранена!");
        }
    }

    public Page<News> getAllNews(Pageable pageable) {
        return newsRepo.findAll(pageable);
    }

    public Page<News> searchByKey(String key, Pageable pageable) {
        return newsRepo.searchByKey(key, pageable);
    }

    public News findNewsById(Long id) {
        News news = newsRepo.findNewsById(id)
                .orElseThrow(() -> new RuntimeException("Новость с id=" + id + " не найдена"));
        return news;
    }

    public News findByNewsName(String newsName) {
        News news = newsRepo.findByNewsName(newsName)
                .orElseThrow(() -> new RuntimeException("Новость " + newsName + " не найдена"));
        return news;
    }


    public void createNews(News news) {
        if (news.getId() != null && newsRepo.findNewsById(news.getId()).isPresent()) {
            throw new RuntimeException("Такая новость уже существует!");
        }
        newsRepo.save(news);
    }

    public void editNews(Long id, News editedNews) {
        News news = findNewsById(id);
        news.setNewsName(editedNews.getNewsName());
        news.setDescription(editedNews.getDescription());
        news.setText(editedNews.getText());

        saveNews(news);
    }

    public void deleteNews(Long id) {
        if (!newsRepo.existsById(id))
            throw new RuntimeException("Новость с id=" + id + " не существует!");

        newsRepo.deleteById(id);
    }
}
