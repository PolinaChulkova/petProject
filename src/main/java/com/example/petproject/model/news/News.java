package com.example.petproject.model.news;

import com.example.petproject.model.Comment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    private List<Comment> comments;
}