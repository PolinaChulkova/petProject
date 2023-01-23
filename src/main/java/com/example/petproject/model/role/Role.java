package com.example.petproject.model.role;

import com.example.petproject.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "roles")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private ERole roleName;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Role(ERole roleName) {
        this.roleName = roleName;
    }
}
