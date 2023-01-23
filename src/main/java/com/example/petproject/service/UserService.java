package com.example.petproject.service;

import com.example.petproject.DTO.UserUpdateRequest;
import com.example.petproject.model.User;
import com.example.petproject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username);
    }

    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("Пользователь с именем " + username + " не найден!"));
    }

    public Page<User> searchUserByEmail(String email, Pageable pageable) {
        return userRepo.searchUserByEmail(email, pageable);
    }

    public User findUserById(long id) {
        return userRepo.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id = " + id + "не найден!"));
    }


    public Page<User> getUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    public User saveUser(User user) {
        try {
            return userRepo.save(user);

        } catch (RuntimeException e) {
            log.error("Пользователь {} не сохранён! Error: [{}].", user.getUsername(), e);
            throw new PersistenceException(String.format("Пользователь %s не сохранён! " +
                    "Error: [%s]", user.getUsername(), e));
        }
    }

    public User updateUser(String username, UserUpdateRequest request) {
        User user = findUserByUsername(username);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return saveUser(user);
    }

    public void deleteUser(String username) {
        if (userRepo.existsByUsername(username)) {
            userRepo.deleteByUsername(username);

        } else throw new EntityNotFoundException("Пользователь с именем \"" + username + "\" не найден!");
    }

    public void loadAvatar(String username, String uri) {
        User user = findUserByUsername(username);
        user.setAvatar(uri);
        saveUser(user);
    }
}
