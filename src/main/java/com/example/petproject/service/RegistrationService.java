package com.example.petproject.service;

import com.example.petproject.DTO.UserDataRequest;
import com.example.petproject.model.User;
import com.example.petproject.model.role.Role;
import com.example.petproject.repository.RoleRepo;
import com.example.petproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public User register(UserDataRequest registrationRequest) {
        User user = new User(registrationRequest);
        if (user.getId() != null && userRepo.findUserById(user.getId()).isPresent()) {
            throw new RuntimeException("Такой пользователь уже существует!");
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        Set<Role> roles = registrationRequest.getRoles().stream()
                .map(r -> roleRepo.findByRoleName(r)
                        .orElseThrow(() -> new RuntimeException("Роль " + r + " не существует")))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        userRepo.save(user);
        return user;
    }
}
