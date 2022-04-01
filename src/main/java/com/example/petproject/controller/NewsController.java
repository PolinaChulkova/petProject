package com.example.petproject.controller;

import com.example.petproject.model.news.News;
import com.example.petproject.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private NewsService newsService;

    @GetMapping("/all")
    public List<News> getAllNews() {
       return newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public News getNews(@PathVariable("id") long id) {
        return newsService.findNewsById(id);
    }

    @PostMapping()
    public News createNews(@RequestBody News news) {
        News createdNews = newsService.createNews(news);
        return createdNews;
    }

    @PutMapping("/{id}")
    public String editNews(@PathVariable("id") long id,
                           @RequestBody News news) {
        News oldNews = newsService.findNewsById(id);
        newsService.editNews(news, oldNews);
        return "The news has been edited";
    }

    @DeleteMapping("/{id}")
    public String deleteNews(@PathVariable("id") long id) {
        newsService.deleteNews(id);
        return "News deleted";
    }

}
