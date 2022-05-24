package com.example.petproject.service;

import com.example.petproject.model.Role;
import com.example.petproject.model.User;
import com.example.petproject.repository.RoleRepo;
import com.example.petproject.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService{
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    public RoleService(RoleRepo roleRepo, UserRepo userRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    public void saveRole(Role role) {
        try {

            roleRepo.save(role);

        } catch (RuntimeException ex) {
            System.out.println("Роль " + role.getRoleName() + " не сохранена!");;
        }
    }

    public Role findRoleByRoleName(String name) {
        Role role =  roleRepo.findRoleByRoleName(name);
        if (role==null) {
            throw new RuntimeException("Роль " + name + " не найдена.");
        }
        return role;
    }

    public Role findRoleById(long id) {
        Role role =  roleRepo.findRoleById(id);
        if (role==null) {
            throw new RuntimeException("Роль с id=" + id + " не найдена.");
        }
        return role;
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Role createNewRole(Role role) {
        if (roleRepo.existsByRoleName(role.getRoleName())) {
            throw new RuntimeException("Роль " + role.getRoleName() + " уже существует!");
        }
        roleRepo.save(role);
        return role;
    }

    public Role deleteRoleByRoleName(String name) {
        if (roleRepo.existsByRoleName(name)) {
            return roleRepo.deleteRoleByRoleName(name);
        } else throw new RuntimeException("Роль " + name + " не существует!");
    }

    public void removeRolesFromUser(User user) {
        user.getRoles().clear();
    }

//     public void addNewRole(Role role) {
//        if (roleRepo.findById(role.getRoleId()) == null) {
//            roleRepo.save(role);
//        }
//     }

}
