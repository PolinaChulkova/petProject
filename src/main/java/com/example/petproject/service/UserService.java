package com.example.petproject.service;

import com.example.petproject.DTO.UserUpdateRequest;
import com.example.petproject.model.User;
import com.example.petproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Пользователь с именем: " + username + " не найден"));
        return user;
    }

    public void saveUser(User user) {
        try {
            userRepo.save(user);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage() + " Пользователь " + user.getUsername() + " не сохранён");
        }
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User findUserById(long id) {
        User user = userRepo.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с id: " + id + "не найден"));
        return user;
    }

    public void updateUser(String username, UserUpdateRequest request) {
        User user = userRepo.findByUsername(username).orElseThrow();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        saveUser(user);
    }

    public void deleteUser(String username) {
        if (userRepo.findByUsername(username).isPresent()) {
            userRepo.deleteByUsername(username);
        } else throw new RuntimeException("Пользователя с именем " + username + " не существует!");
    }

    public void loadAvatar(String username, String uri) {
        User user = userRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с именем: " + username + " не найден"));
        user.setAvatar(uri);
        saveUser(user);
    }
}
