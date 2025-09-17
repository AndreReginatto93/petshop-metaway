package com.example.petshop.entities;

import com.example.petshop.entities.contato.ContatoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String cpf;
    @CreationTimestamp
    private String dataCadastro;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<EnderecoEntity> enderecos;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<ContatoEntity> contatos;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<PetEntity> pets;
}
