package com.example.petproject.model;

import com.example.petproject.model.news.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
//    @JoinTable(name = "user_comments",
//    joinColumns = @JoinColumn(name = "comment_id"),
//    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "news_id")
    private News news;
}
