package com.example.petproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "text")
    private String text;

    @Column(name = "parent_id")
    private Long parentId;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "news_id")
    private News news;

    public Comment(Date date, String text, Long parentId, User author, News news) {
        this.date = date;
        this.text = text;
        this.parentId = parentId;
        this.author = author;
        this.news = news;
    }
}
