package com.example.petshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enderecos")
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String complemento;
    private String tag;
}
