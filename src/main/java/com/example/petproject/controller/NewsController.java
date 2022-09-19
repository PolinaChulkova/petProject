package com.example.petproject.controller;

import com.example.petproject.DTO.MessageResponse;
import com.example.petproject.model.Comment;
import com.example.petproject.model.News;
import com.example.petproject.service.CommentService;
import com.example.petproject.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<News>> getAllNews() {
       return ResponseEntity.ok().body(newsService.getAllNews());
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
    @RequestMapping(method = RequestMethod.GET, value = "/comments/{newsId}")
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable Long newsId,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Comment> comments = commentService.getComments(newsId, pageable);
        return ResponseEntity.ok().body(comments.getContent());
    }
}
