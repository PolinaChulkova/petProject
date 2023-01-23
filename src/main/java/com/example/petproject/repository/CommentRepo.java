package com.example.petproject.repository;

import com.example.petproject.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(long id);
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findAllByNews_Id(long newsId, Pageable pageable);
}
