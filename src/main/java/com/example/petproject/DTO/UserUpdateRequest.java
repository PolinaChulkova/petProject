package com.example.petproject.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserUpdateRequest {
    private final String username;
    private final String email;
    private final String password;
}
