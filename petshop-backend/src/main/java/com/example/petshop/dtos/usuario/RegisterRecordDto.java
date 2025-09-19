package com.example.petshop.dtos.usuario;

import com.example.petshop.entities.usuario.UserRole;

public record RegisterRecordDto(String login,
                                String nome,
                                String password,
                                UserRole role) {
}
