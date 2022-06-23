package com.example.petproject.controller;

import com.example.petproject.model.Comment;
import com.example.petproject.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Api(description ="Контроллер для работы с комментариями")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation("Получение комментария")
    @RequestMapping(method = RequestMethod.GET, value = "/{commentId}")
    @ResponseBody
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(commentService.findCommentById(commentId));
    }

    @ApiOperation("Создание комментария")
    @RequestMapping(method = RequestMethod.POST, value = "/{authorId}/{newsId}")
    @ResponseBody
    public ResponseEntity<Comment> createComment(@PathVariable Long authorId,
                                @PathVariable Long newsId,
                                @RequestBody Comment comment) {
        return ResponseEntity.ok().body(commentService.createComment(authorId, newsId, comment));
    }

    @ApiOperation("Редактирование комментария")
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public ResponseEntity<Comment> updateComment(@PathVariable Long id,
                                @RequestBody Comment editedComment) {
        Comment comment = commentService.findCommentById(id);
        commentService.updateComment(editedComment, comment);
        return ResponseEntity.ok().body(comment);
    }

    @ApiOperation("Удаление комментария")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok().body(commentService.deleteById(id));
    }
}
