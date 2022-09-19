package com.example.petproject.DTO;

import com.example.petproject.model.role.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@RequiredArgsConstructor
@Getter@Setter
public class UserUpdateRequest {
    private String username;
    private String email;
    private String password;
}
