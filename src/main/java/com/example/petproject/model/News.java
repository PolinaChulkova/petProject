package com.example.petproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Table(name = "news")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "news_id")
    private Long id;
    @Column(name = "news_name")
    private String newsName;
    @Column(name = "publication_date")
    private LocalDateTime publicationDate = LocalDateTime.now();
    @Column(name = "description")
    private String description;
    @Column(name = "news_text")
    private String text;

    @JsonBackReference
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public News(String newsName, String description, String text) {
        this.newsName = newsName;
        this.description = description;
        this.text = text;
    }
}