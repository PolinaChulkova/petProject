package com.example.petproject.service;

import com.example.petproject.model.Comment;
import com.example.petproject.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;

    public List<Comment> getComments() {
        return commentRepo.findAll();
    }

    public Comment findCommentById(long id){
        if (commentRepo.existsById(id)) {
            Comment comment = commentRepo.findCommentById(id);
            return comment;
        } else {
            return null;//ошибку
        }
    }

    public Comment createComment(Comment comment) {
        if (comment.getId()!=null && commentService.findCommentById(comment.getId()) != null) {
            return null;
        }
        commentRepo.save(comment);
        return comment;
    }

    public Comment deleteById(long id){
        if (commentRepo.existsById(id)) {
            return commentRepo.deleteById(id);
        } else {
            return null;//ошибку
        }
    }

    public Comment save(Comment comment) {
        return commentRepo.save(comment);
    }
}
