package com.example.petproject.controller;

import com.example.petproject.DTO.JwtResponse;
import com.example.petproject.DTO.LoginRequest;
import com.example.petproject.DTO.MessageResponse;
import com.example.petproject.DTO.UserDataRequest;
import com.example.petproject.config.jwt.JwtProvider;
import com.example.petproject.model.User;
import com.example.petproject.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(description = "Контроллер для аутентификации")
@AllArgsConstructor
@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthService registrationService;

    @PostMapping("login")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        String jwt = jwtProvider.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()));
    }

    @PostMapping("register")
    public ResponseEntity<?> registration(@RequestBody UserDataRequest registrationRequest) {
        registrationService.register(registrationRequest);
        return ResponseEntity.ok(new MessageResponse("User created"));
    }

    @ApiOperation("Выход из аккаунта")
    @GetMapping("/logout")
    public ResponseEntity<?> logout(Principal principal) {
        String username = principal.getName();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body(new MessageResponse(username + ", вы вышли из аккаунта"));
    }
}
