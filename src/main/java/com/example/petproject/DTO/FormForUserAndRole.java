package com.example.petproject.DTO;

import com.example.petproject.model.User;
import lombok.Data;

@Data
public class FormForUserAndRole {
    private User user;
    private String roleName;
}
