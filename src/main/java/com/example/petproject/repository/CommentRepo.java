package com.example.petproject.repository;

import com.example.petproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    Comment findCommentById(long id);
    boolean existsById(long id);
    Comment deleteById(long id);
    Comment save(Comment comment);
    List<Comment> findAll();
    List<Comment> findAllByNews_Id(Long newsId);
}
