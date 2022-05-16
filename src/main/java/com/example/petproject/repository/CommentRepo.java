package com.example.petproject.repository;

import com.example.petproject.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    Comment findCommentById(long id);
    boolean existsById(long id);
    Comment deleteById(long id);
    Comment save(Comment comment);
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findAllByNews_Id(long newsId, Pageable pageable);
}
