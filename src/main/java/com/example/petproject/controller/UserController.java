package com.example.petproject.controller;

import com.example.petproject.DTO.UserDataDTO;
import com.example.petproject.model.User;
import com.example.petproject.service.RoleService;
import com.example.petproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody UserDataDTO form) {
        User user = userService.createNewUser(form.getUser(), form.getRoleName());
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> updateUser(@PathVariable("id") long id,
                             @RequestBody UserDataDTO form) {
        User user = userService.findUserById(id);
        roleService.removeRolesFromUser(user);
        userService.updateUser(user, form);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }
}
