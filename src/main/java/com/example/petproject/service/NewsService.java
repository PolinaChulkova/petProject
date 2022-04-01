package com.example.petproject.service;

import com.example.petproject.model.news.News;
import com.example.petproject.repository.NewsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public void saveNews(News news) {
        newsRepo.save(news);
    }

    public List<News> getAllNews() {
        return newsRepo.findAll();
    }

    public News findNewsById(long id) {
        News news = newsRepo.findNewsById(id);
        if (news == null) {
            return null;
        }
        return news;
    }

    public News findByNewsName(String newsName) {
        return newsRepo.findByNewsName(newsName);
    }


    public News createNews(News news) {
        if (news.getId() != null && newsRepo.findNewsById(news.getId()) != null) {
            return null;
        }
        newsRepo.save(news);
        return news;
    }

    public void editNews(News news, News oldNews) {
        oldNews.setNewsName(news.getNewsName());
        oldNews.setDescription(news.getDescription());
        oldNews.setStatus(news.getStatus());
        oldNews.setText(news.getText());
        oldNews.setPublicationDate(news.getPublicationDate());
        newsRepo.save(oldNews);
    }

    public void deleteNews(long id) {
        newsRepo.deleteById(id);
    }
}
