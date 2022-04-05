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
        roleRepo.save(role);
    }

    public Role findRoleByRoleName(String name) {
        return roleRepo.findRoleByRoleName(name);
    }

    public Role findRoleById(long id) {
        return roleRepo.findRoleById(id);
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Role createNewRole(Role role) {
        if (roleRepo.existsByRoleName(role.getRoleName())) {
            return null;//exception
        }
        roleRepo.save(role);
        return role;
    }

    public Role deleteRoleByRoleName(String name) {
        if (roleRepo.existsByRoleName(name)) {
            return roleRepo.deleteRoleByRoleName(name);
        } else return null;//exception
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
