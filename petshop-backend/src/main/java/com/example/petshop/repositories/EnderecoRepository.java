package com.example.petshop.repositories;

import com.example.petshop.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
    @Query(value = """
        SELECT e.id, e.cliente_id, e.logradouro, e.cidade, e.bairro, e.complemento, e.tag
        FROM enderecos e
        join clientes cl on cl.id = e.cliente_id
        WHERE cl.cpf = :login
    """, nativeQuery = true)
    List<EnderecoEntity> findByClienteLogin(@Param("login") String login);
}
