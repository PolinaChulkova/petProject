package com.example.petproject.service;

import com.example.petproject.model.Comment;
import com.example.petproject.repository.CommentRepo;
import com.example.petproject.repository.NewsRepo;
import com.example.petproject.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final NewsRepo newsRepo;

    public CommentService(CommentRepo commentRepo, UserRepo userRepo, NewsRepo newsRepo) {
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.newsRepo = newsRepo;
    }

    public List<Comment> getComments(Long newsId) {
        return commentRepo.findAllByNews_Id(newsId);
    }

    public Comment findCommentById(Long id){
        if (commentRepo.existsById(id)) {
            Comment comment = commentRepo.findCommentById(id);
            return comment;
        } else {
            return null;//ошибку
        }
    }

    public Comment createComment(Long authorId, Long newsId, Comment comment) {
        if (commentRepo.existsById(comment.getId())) {
            return null;
        }
        comment.setAuthor(userRepo.findUserById(authorId));
        comment.setNews(newsRepo.findNewsById(newsId));
        commentRepo.save(comment);
        return comment;
    }

    public void updateComment(Comment comment, Comment oldComment) {
        oldComment.setText(comment.getText());
        commentRepo.save(oldComment);
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
