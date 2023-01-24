package com.example.petproject.controller;

import com.example.petproject.DTO.CommentDTO;
import com.example.petproject.model.Comment;
import com.example.petproject.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api("Контроллер для работы с комментариями")
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ApiOperation("Получение комментарияпо его id")
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(commentService.findCommentById(commentId));
    }

    @ApiOperation("Создание комментария текущим пользователем")
    @PostMapping("/{newsId}")
    public ResponseEntity<?> createComment(@PathVariable Long newsId,
                                           @RequestBody CommentDTO commentDTO,
                                           Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok().body(commentService.createComment(username, newsId, commentDTO));
    }

    @ApiOperation("Редактирование комментария текущего пользователя по его id")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @RequestBody CommentDTO commentDTO,
                                           Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok().body(commentService.updateComment(username, id, commentDTO));
    }

    @ApiOperation("Удаление комментария текущего пользователя по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        commentService.deleteById(username, id);
        return ResponseEntity.ok().body("Комментарий пользователя " + username + " удалён.");
    }
}
