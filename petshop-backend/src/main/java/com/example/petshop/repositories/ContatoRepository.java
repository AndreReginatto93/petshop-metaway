package com.example.petshop.repositories;

import com.example.petshop.entities.contato.ContatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ContatoRepository extends JpaRepository<ContatoEntity, Long> {
    @Query(value = """
        SELECT c.id, cliente_id, tag, tipo, valor
        FROM contatos c
        join clientes cl on cl.id = c.cliente_id
        WHERE cl.cpf = :login
    """, nativeQuery = true)
    List<ContatoEntity> findByClienteLogin(@Param("login") String login);
}
