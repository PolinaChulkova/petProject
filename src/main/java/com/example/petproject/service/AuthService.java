package com.example.petproject.service;

import com.example.petproject.DTO.UserDataRequest;
import com.example.petproject.model.User;
import com.example.petproject.model.role.Role;
import com.example.petproject.repository.RoleRepo;
import com.example.petproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public User register(UserDataRequest registrationRequest) {
        User user = new User(registrationRequest);

        if (userRepo.existsByUsername(registrationRequest.getUsername())) {
            throw new EntityExistsException("Пользователь " + registrationRequest.getUsername() + " уже существует!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = registrationRequest.getRoles().stream()
                .map(r -> roleRepo.findByRoleName(r)
                        .orElseThrow(() -> new EntityNotFoundException("Роль " + r.name() + " не найдена!")))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        userRepo.save(user);
        return user;
    }
}
