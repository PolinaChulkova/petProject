package com.example.petproject.controller;

import com.example.petproject.model.Role;
import com.example.petproject.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Api(description = "Контроллер для работы ролями пользователей")
public class RoleController {

    private final RoleService roleService;

    @ApiOperation("Получение списка всех возможных ролей")
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(roleService.findAll());
    }

    @ApiOperation("Создание роли")
    @PostMapping
    @ResponseBody
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(roleService.createNewRole(role));
    }

    @ApiOperation("Удаление роли")
    @DeleteMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Role> deleteRole(@PathVariable String name) {
        return ResponseEntity.ok().body(roleService.deleteRoleByRoleName(name));
    }
}
