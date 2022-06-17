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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final CommentService commentService;

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<News>> getAllNews() {
       return ResponseEntity.ok().body(newsService.getAllNews());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<News> getNews(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(newsService.findNewsById(id));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return ResponseEntity.ok().body(newsService.createNews(news));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<News> editNews(@PathVariable("id") long id,
                           @RequestBody News editedNews) {
        News news = newsService.findNewsById(id);
        newsService.editNews(editedNews, news);
        return ResponseEntity.ok().body(news);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<News> deleteNews(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(newsService.deleteNews(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{newsId}")
    @ResponseBody
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable Long newsId,
            @RequestParam(value = "size", required = false, defaultValue = "0") int size,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Comment> comments = commentService.getComments(newsId, pageable);
        return ResponseEntity.ok().body(comments.getContent());
    }
}
