package com.example.petproject.service;

import com.example.petproject.DTO.CommentDTO;
import com.example.petproject.model.Comment;
import com.example.petproject.model.User;
import com.example.petproject.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepo commentRepo;
    private final UserService userService;
    private final NewsService newsService;

    public Page<Comment> getComments(Long newsId, Pageable pageable) {
        Page<Comment> comments = commentRepo.findAllByNews_Id(newsId, pageable);
        return comments;
    }

    public Comment findCommentById(Long id) {
        Comment comment = commentRepo.findCommentById(id)
                .orElseThrow(() -> new RuntimeException("Коменнтарий с id=" + id + " не найден"));
        return comment;
    }

    public void createComment(String authorName, Long newsId, CommentDTO commentDTO) {
        User author = (User) userService.loadUserByUsername(authorName);
        Comment newComment = new Comment(
                new Date(),
                commentDTO.getText(),
                commentDTO.getParentId(),
                author,
                newsService.findNewsById(newsId));

        save(newComment);
    }

    public void updateComment(String username, Long commentId, CommentDTO commentDTO) {
        try {
            if (!matchersUser(username, commentId))
                throw new RuntimeException("Комментарий нельзя отредактировать");
            Comment oldComment = findCommentById(commentId);
            oldComment.setText(commentDTO.getText());
            save(oldComment);

        } catch (RuntimeException e) {
            log.error("Комментарий " + commentId + " не обновлен");
        }
    }

    public void deleteById(String username, Long commentId) {
        if (!matchersUser(username, commentId))
            throw new RuntimeException("Комментарий нельзя отредактировать");

        commentRepo.deleteById(commentId);
    }

    public boolean matchersUser(String username, Long commentId) {
        User user = (User) userService.loadUserByUsername(username);
        Comment comment = findCommentById(commentId);
        return user.getComments().contains(comment);
    }

    public void save(Comment comment) {
        try {
            commentRepo.save(comment);

        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage()
                    + " Комментарий " + comment.getAuthor().getUsername() + " не сохранён");
        }
    }
}
