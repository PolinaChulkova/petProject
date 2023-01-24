package com.example.petproject.controller;

import com.example.petproject.DTO.UserUpdateRequest;
import com.example.petproject.model.User;
import com.example.petproject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api("Контроллер для работы с пользователями")
public class UserController {

    private final UserService userService;

    @ApiOperation("Получение списка всех пользователей")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "DESC") String order,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(order), sort));
        return ResponseEntity.ok().body(userService.getUsers(pageable).getContent());
    }

    @ApiOperation("Получение пользователя по его id")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    @ApiOperation("Поиск пользователя по email")
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserByText(
            @RequestParam("text") String email,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "DESC") String order,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(order), sort));
        return ResponseEntity.ok().body(userService.searchUserByText(email, pageable).getContent());
    }

    @ApiOperation("Обновление текущего пользователя")
    @PutMapping("/update")
    @Transactional
    public ResponseEntity<?> updateCurrentUser(@RequestBody UserUpdateRequest request, Principal principal) {
        String username = principal.getName();
        User user = userService.updateUser(username, request);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body(user);
    }

    @ApiOperation("Удаление текущего пользователя")
    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<?> deleteUser(Principal principal) {
        String username = principal.getName();
        userService.deleteUser(username);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("Пользльзователь " + principal.getName() + " удалён.");
    }

    @ApiOperation("Обновление пользователя по username")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String username,
                                        @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok().body(userService.updateUser(username, request));
    }

    @ApiOperation("Удаление пользователя по username")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().body("Пользователь " + username + " удалён.");
    }

}
