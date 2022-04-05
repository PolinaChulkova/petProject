package com.example.petproject.controller;

import com.example.petproject.model.Comment;
import com.example.petproject.model.news.News;
import com.example.petproject.service.CommentService;
import com.example.petproject.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final CommentService commentService;

    @GetMapping("/all")
    public List<News> getAllNews() {
       return newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public News getNews(@PathVariable("id") long id) {
        return newsService.findNewsById(id);
    }

    @PostMapping()
    public News createNews(@RequestBody News news) {
        News createdNews = newsService.createNews(news);
        return createdNews;
    }

    @PutMapping("/{id}")
    public String editNews(@PathVariable("id") long id,
                           @RequestBody News news) {
        News oldNews = newsService.findNewsById(id);
        newsService.editNews(news, oldNews);
        return "The news has been edited";
    }

    @DeleteMapping("/{id}")
    public String deleteNews(@PathVariable("id") Long id) {
        newsService.deleteNews(id);
        return "News deleted";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{newsId}")
    public Set<Comment> getComments(@PathVariable Long newsId) {
        News news = newsService.findNewsById(newsId);
        Set<Comment> comments = news.getComments();
        return comments;
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/comment/{newsId}/{commentId}")
//    public Comment getComment(@PathVariable Long id) {
//        return commentService.findCommentById(id);
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/comment/{authorId}/{newsId}")
    public String createComment(@PathVariable Long authorId,
                                @PathVariable Long newsId,
                                @RequestBody Comment comment) {
        commentService.createComment(authorId, newsId, comment);
        return "New comment created";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/comment/{id}")
    public String updateComment(@PathVariable Long id,
                                @RequestBody Comment comment) {
        Comment oldComment = commentService.findCommentById(id);
        commentService.updateComment(comment, oldComment);
        return "Comment update";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/comment/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return "Comment deleted";
    }
}
