package com.example.petproject.DTO;

import com.example.petproject.model.role.ERole;
import com.example.petproject.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserDataRequest {

    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Set<ERole> roles;

    public Set<Role> convertToRoleSet() {
        return roles.stream().map(r -> new Role(r)).collect(Collectors.toSet());
    }
}
