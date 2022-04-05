package com.example.petproject.model.news;

import com.example.petproject.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "news_id")
    private Long id;
    @Column(name = "news_name")
    private String newsName;
    @Column(name = "publication_date")
    private Date publicationDate;
    @Column(name = "description")
    private String description;
    @Column(name = "news_text")
    private String text;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NewsStatus status;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;
}