package com.example.petproject.repository;

import com.example.petproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(String name);
    Role findRoleById(long id);
    Role save(Role role);
    boolean existsById(long id);
    Role deleteRoleByRoleName(String roleName);
    List<Role> findAll();
    boolean existsByRoleName(String roleName);
}
