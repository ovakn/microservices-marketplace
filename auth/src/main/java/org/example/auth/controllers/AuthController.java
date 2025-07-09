package org.example.auth.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.auth.dtos.AuthRequest;
import org.example.auth.dtos.AuthResponce;
import org.example.auth.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
        makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponce> register(@RequestBody AuthRequest request) {
        String token = authService.register(request.email(), request.password(), request.roles());
        return ResponseEntity.ok(new AuthResponce(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponce> login(@RequestBody AuthRequest request) {
        String token = authService.authenticate(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponce(token));
    }
}