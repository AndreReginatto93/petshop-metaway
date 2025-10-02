package com.example.petshop.dtos.usuario;

public record UsuarioReadDTO(Long id,
                             String login,
                             String nome,
                             String role) {
}
