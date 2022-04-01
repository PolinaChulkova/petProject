package com.example.petproject.controller;

import com.example.petproject.model.Role;
import com.example.petproject.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private RoleService roleService;

    @GetMapping("/all")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @PostMapping("/")
    public String createRole(@RequestBody Role role) {
        roleService.createNewRole(role);
        return "Role created";
    }

    @DeleteMapping("/{name}")
    public String deleteRole(@PathVariable String name) {
        roleService.deleteRoleByRoleName(name);
        return "Role is deleted";
    }
}
