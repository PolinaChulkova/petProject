package com.example.petproject.controller;

import com.example.petproject.model.Comment;
import com.example.petproject.model.news.News;
import com.example.petproject.service.CommentService;
import com.example.petproject.service.NewsService;
import com.example.petproject.tree.TypeAdapterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final TypeAdapterImpl typeAdapter;
    private final CommentService commentService;
//    private final CommentRepo commentRepo;

    @GetMapping("/all")
    public List<News> getAllNews() {
       return newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public News getNews(@PathVariable("id") long id) {
        return newsService.findNewsById(id);
    }

    @PostMapping
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
    public String deleteNews(@PathVariable("id") Long id) {
        newsService.deleteNews(id);
        return "News deleted";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{newsId}")
    public List<Comment> getComments(
            @PathVariable Long newsId,
            @RequestParam(value = "size", required = false, defaultValue = "0") int size,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Comment> comments = commentService.getComments(newsId, pageable);
        return comments.getContent();
    }
}
