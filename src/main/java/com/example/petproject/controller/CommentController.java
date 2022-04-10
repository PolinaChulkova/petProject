package com.example.petproject.controller;

import com.example.petproject.model.Comment;
import com.example.petproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @RequestMapping(method = RequestMethod.GET, value = "/{commentId}")
    public Comment getComment(@PathVariable Long commentId) {
        return commentService.findCommentById(commentId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{authorId}/{newsId}")
    public String createComment(@PathVariable Long authorId,
                                @PathVariable Long newsId,
                                @RequestBody Comment comment) {
        commentService.createComment(authorId, newsId, comment);
        return "New comment created";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public String updateComment(@PathVariable Long id,
                                @RequestBody Comment comment) {
        Comment oldComment = commentService.findCommentById(id);
        commentService.updateComment(comment, oldComment);
        return "Comment update";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return "Comment deleted";
    }
}
