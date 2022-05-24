package com.example.petproject.service;

import com.example.petproject.model.Comment;
import com.example.petproject.repository.CommentRepo;
import com.example.petproject.repository.NewsRepo;
import com.example.petproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final NewsRepo newsRepo;
//    private final TypeAdapterImpl typeAdapter;

    public Page<Comment> getComments(Long newsId, Pageable pageable) {
        Page<Comment> comments = commentRepo.findAllByNews_Id(newsId, pageable);

//        return CommentNode.makeTree(comments.getContent(), typeAdapter);
        return comments;
    }

    public Comment findCommentById(Long id){
        Comment comment = commentRepo.findCommentById(id);
        if (comment == null) {
            throw new RuntimeException("Коменнтарий с id=" + id + " не найден");
        }
        return comment;
    }

    public Comment createComment(Long authorId, Long newsId, Comment comment) {
        if (comment.getId() != null && commentRepo.findCommentById(comment.getId()) != null){
            return null;
        }
        comment.setAuthor(userRepo.findUserById(authorId));
        comment.setNews(newsRepo.findNewsById(newsId));
        commentRepo.save(comment);
        return comment;
    }

    public void updateComment(Comment comment, Comment oldComment) {
        try {
            oldComment.setText(comment.getText());

        } catch (RuntimeException re) {
            System.out.println("Комментарий " + comment.getId() + " не обновлен");
        }

        commentRepo.save(oldComment);
    }

    public Comment deleteById(long id){
        if (commentRepo.existsById(id)) {
            return commentRepo.deleteById(id);
        } else throw new RuntimeException("Комментарий с id=" + id + " не существует!");
    }

    public Comment save(Comment comment) {
        Comment thisComment = commentRepo.save(comment);
        if (thisComment==null) {
            throw new RuntimeException(comment.getAuthor() + " ваш комментарий не сохранен");
        }
        return thisComment;
    }
}
