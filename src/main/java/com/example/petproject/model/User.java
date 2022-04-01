package com.example.petproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;
    @Column(name = "city")
    private String city;
//    @Column(name = "password")
//    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role"
            ,joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Set<Comment> comments;
}
