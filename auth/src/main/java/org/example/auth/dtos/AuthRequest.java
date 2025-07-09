package org.example.auth.dtos;

import org.example.auth.entities.Role;
import java.util.Set;

public record AuthRequest(String email, String password, Set<Role> roles) {}