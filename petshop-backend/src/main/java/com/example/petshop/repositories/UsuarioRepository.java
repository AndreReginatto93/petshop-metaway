package com.example.petshop.repositories;

import com.example.petshop.entities.usuario.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);
}
