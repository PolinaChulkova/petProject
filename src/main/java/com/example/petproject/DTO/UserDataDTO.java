package com.example.petproject.DTO;

import com.example.petproject.model.User;
import lombok.*;

@Data
public class UserDataDTO {
    private User user;
    private String roleName;
}
