package com.example.petproject.DTO;

import com.example.petproject.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class JwtResponse {

    private String token;
    private final String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private Set<Role> role;

}
