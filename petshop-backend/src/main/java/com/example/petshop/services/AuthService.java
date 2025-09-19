package com.example.petshop.services;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    public UserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new RuntimeException("Usuário não autenticado");
        }
        return (UserDetails) authentication.getPrincipal();
    }

    public String getAuthenticatedUsername() {
        return getAuthenticatedUser().getUsername();
    }
}