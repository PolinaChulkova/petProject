package com.example.petproject.controller;

import com.example.petproject.DTO.FormForUserAndRole;
import com.example.petproject.exceptions.ModelException;
import com.example.petproject.model.User;
import com.example.petproject.service.RoleService;
import com.example.petproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") long id) {
        User user = userService.findUserById(id);
        return user.toString();
    }

    @PostMapping
    public String createUser(@RequestBody FormForUserAndRole form) throws ModelException {
        User user = userService.createNewUser(form.getUser(), form.getRoleName());
        return "User created";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @RequestBody FormForUserAndRole form) {
        User user = userService.findUserById(id);
        roleService.removeRolesFromUser(user);
        userService.updateUser(user, form);
        return "User update";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "User deleted";
    }
}
