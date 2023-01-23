package com.example.petproject.service;

import com.example.petproject.model.News;
import com.example.petproject.repository.NewsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepo newsRepo;


    public Page<News> getAllNews(Pageable pageable) {
        return newsRepo.findAll(pageable);
    }

    public Page<News> searchByKey(String key, Pageable pageable) {
        return newsRepo.searchByKey(key, pageable);
    }

    public News findNewsById(Long id) {
        return newsRepo.findNewsById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Новость с id =" + id + " не найдена!"));
    }

    public void createNews(News news) {
        if (newsRepo.existsByNewsNameAndPublicationDate(news.getNewsName(), news.getPublicationDate())) {
            throw new EntityExistsException("Новость " + news.getNewsName() + " уже существует!");
        }
        newsRepo.save(news);
    }

    public void updateNews(Long id, News editedNews) {
        News news = findNewsById(id);
        news.setNewsName(editedNews.getNewsName());
        news.setDescription(editedNews.getDescription());
        news.setText(editedNews.getText());

        saveNews(news);
    }

    public void saveNews(News news) {
        try {
            newsRepo.save(news);

        } catch (RuntimeException e) {
            log.error("Новость {} не сохранена! Error: [{}].", news.getNewsName(), e.getLocalizedMessage());
            throw new RuntimeException(String.format("Новость %s не сохранена! Error: [%s].",
                    news.getNewsName(), e));
        }
    }

    public void deleteNews(Long id) {
        if (!newsRepo.existsById(id))
            throw new EntityNotFoundException("Новость с id = " + id + " не найдена!");

        newsRepo.deleteById(id);
    }
}
