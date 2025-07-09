package org.example.auth.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.auth.entities.Role;
import org.example.auth.entities.User;
import org.example.auth.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
        makeFinal = true)
public class AuthService {
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;

    public String register(String email, String password, Set<Role> roles) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User(
                null,
                email,
                new BCryptPasswordEncoder().encode(password),
                roles
        );
        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public String authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User does not exist")
                );
        return jwtService.generateToken(user);
    }
}