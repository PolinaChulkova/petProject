package com.example.petproject.controller;

import com.example.petproject.DTO.MessageResponse;
import com.example.petproject.model.Comment;
import com.example.petproject.model.News;
import com.example.petproject.service.CommentService;
import com.example.petproject.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Api(description = "Контроллер для работы с новостями")
public class NewsController {

    private final NewsService newsService;
    private final CommentService commentService;

    @ApiOperation("Получение списка новостей")
    @GetMapping("/all")
    public ResponseEntity<List<News>> getAllNews(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publicationDate").ascending());
        Page<News> news = newsService.getAllNews(pageable);
       return ResponseEntity.ok().body(news.getContent());
    }

    @ApiOperation("Поиск новостей по ключевому слову")
    @GetMapping("/search")
    public ResponseEntity<List<News>> searchByKey(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size
    ) {
        Pageable pageable = PageRequest.of(0, 2);
        Page<News> news = newsService.searchByKey(key, pageable);
        return ResponseEntity.ok().body(news.getContent());
    }

    @ApiOperation("Получение новости по id")
    @GetMapping("/{id}")
    public ResponseEntity<News> getNews(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(newsService.findNewsById(id));
    }

    @ApiOperation("Создание новости")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createNews(@RequestBody News news) {
        newsService.createNews(news);
        return ResponseEntity.ok().body(new MessageResponse("Создана публикация: " + news.getNewsName()));
    }

    @ApiOperation("Редактирование новости")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editNews(@PathVariable("id") Long id,
                           @RequestBody News editedNews) {
        newsService.editNews(id, editedNews);
        return ResponseEntity.ok().body(
                new MessageResponse("Новость " + editedNews.getNewsName() + " отредактирована"));
    }

    @ApiOperation("Удаление новости по её id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok().body(new MessageResponse("Новость с id=" + id + " удалена"));
    }

//    страница нумеруется с 0

    @ApiOperation("Получение списка комментариев к новости по её id")
    @GetMapping("/comments/{newsId}")
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable Long newsId,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Comment> comments = commentService.getComments(newsId, pageable);
        return ResponseEntity.ok().body(comments.getContent());
    }
}
