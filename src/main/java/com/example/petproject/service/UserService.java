package com.example.petproject.service;

import com.example.petproject.exceptions.ModelException;
import com.example.petproject.exceptions.NameError;
import com.example.petproject.model.Role;
import com.example.petproject.DTO.FormForUserAndRole;
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
        userRepo.save(user);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User findUserById(long id) {
        User user = userRepo.findUserById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void addRoleToUser(User user, String roleName) {
        Role role = roleRepo.findRoleByRoleName(roleName);
        user.getRoles().add(role);
        saveUser(user);
    }

    public User createNewUser(User user, String roleName) throws ModelException {
        if (user.getId() != null && userRepo.findUserById(user.getId()) != null) {
            throw new ModelException(NameError.ERROR_IS_EXIST);
        }
        addRoleToUser(user, roleName);
        userRepo.save(user);
        return user;
    }

    public void updateUser(User user, FormForUserAndRole form) {
        User newUser = form.getUser();
        String roleName = form.getRoleName();

        user.setUsername(newUser.getUsername());
        user.setAge(newUser.getAge());
        user.setEmail(newUser.getEmail());
        user.setCity(newUser.getCity());

        addRoleToUser(user, roleName);

//        long idOldUser = user.getId();
//        userRepo.deleteById(user.getId());
//        newUser.setId(idOldUser);
//        userRepo.save(newUser);
        userRepo.save(user);
    }

    public User deleteUser(long id) {
        if (userRepo.existsById(id)) {
            return userRepo.deleteById(id);
        } else return null;//exception
    }


}
