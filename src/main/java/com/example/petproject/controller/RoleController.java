package com.example.petproject.controller;

import com.example.petproject.model.Role;
import com.example.petproject.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(roleService.findAll());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(roleService.createNewRole(role));
    }

    @DeleteMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Role> deleteRole(@PathVariable String name) {
        return ResponseEntity.ok().body(roleService.deleteRoleByRoleName(name));
    }
}
