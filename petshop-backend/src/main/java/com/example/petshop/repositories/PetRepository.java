package com.example.petshop.repositories;

import com.example.petshop.entities.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PetRepository extends JpaRepository<PetEntity, Long> {
    @Query(value = """
         SELECT p.id, p.raca_id, p.cliente_id, p.data_nascimento, p.nome
         FROM pets p
         join clientes cl on cl.id = p.cliente_id
         WHERE cl.cpf = :login
    """, nativeQuery = true)
    List<PetEntity> findByClienteLogin(@Param("login") String login);
}
