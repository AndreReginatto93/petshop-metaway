package com.example.petshop.repositories;

import com.example.petshop.entities.AtendimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, Long> {
    @Query(value = """
         SELECT a.id, a.pet_id, a.descricao, a.valor, a.data_atendimento
         FROM atendimentos a
         join pets p on p.id = a.pet_id
         join clientes cl on cl.id = p.cliente_id
         WHERE cl.cpf = :login
    """, nativeQuery = true)
    List<AtendimentoEntity> findByClienteLogin(@Param("login") String login);

    @Query(value = """
         SELECT a.id, a.pet_id, a.descricao, a.valor, a.data_atendimento
         FROM atendimentos a
         where a.pet_id = :pet
    """, nativeQuery = true)
    List<AtendimentoEntity> findByPet(@Param("pet") Long petId);
}
