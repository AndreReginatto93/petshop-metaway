package com.example.petshop.repositories;

import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.entities.usuario.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByCpf(String cpf);
}
