package com.example.petproject.controller;

import com.example.petproject.model.Comment;
import com.example.petproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    public String createComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
        return "New comment created";
    }

}
