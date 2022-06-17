package com.example.petproject.service;

import com.example.petproject.model.Role;
import com.example.petproject.DTO.UserDataDTO;
import com.example.petproject.model.User;
import com.example.petproject.repository.RoleRepo;
import com.example.petproject.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
//    private final BCryptPasswordEncoder encoder;


    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public void saveUser(User user) {
        try {
            userRepo.save(user);

        } catch (RuntimeException re) {
            System.out.println("Пользователь не сохранён!");;
        }
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User findUserById(long id) {
        User user = userRepo.findUserById(id);
        if (user == null) {
            throw new RuntimeException("Пользователь с id=" + id + " не найден");
        }
        return user;
    }

    public User findByUsername(String username) {
        User user =  userRepo.findByUsername(username);
        if (user==null) {
            throw new RuntimeException("Пользователь " + username + " не найден.");
        }
        return user;
    }

    public void addRoleToUser(User user, String roleName) {
        try {

            Role role = roleRepo.findRoleByRoleName(roleName);
            user.getRoles().add(role);

        } catch (RuntimeException re) {
            System.out.println("Роль " + roleName + " не присвоена пользователю " + user.getUsername());
        }
        saveUser(user);
    }

    public User createNewUser(User user, String roleName) {
        if (user.getId() != null && userRepo.findUserById(user.getId()) != null) {
            throw new RuntimeException("Такой пользователь уже существует!");
//            throw new ModelException(ERROR_IS_EXIST.getErrorText());
        }
        addRoleToUser(user, roleName);
        userRepo.save(user);
        return user;
    }

    public void updateUser(User user, UserDataDTO form) {
        try {

            User newUser = form.getUser();
            String roleName = form.getRoleName();

            user.setUsername(newUser.getUsername());
            user.setAge(newUser.getAge());
            user.setEmail(newUser.getEmail());
            user.setCity(newUser.getCity());

            addRoleToUser(user, roleName);

        } catch (RuntimeException re) {
            System.out.println("Пользователь " + user.getId() + " не обновлен");
        }

        userRepo.save(user);
    }

    public User deleteUser(long id) {
        if (userRepo.existsById(id)) {
            return userRepo.deleteById(id);
        } else throw new RuntimeException("Пользователя с id=" + id + " не существует!");
    }
}
