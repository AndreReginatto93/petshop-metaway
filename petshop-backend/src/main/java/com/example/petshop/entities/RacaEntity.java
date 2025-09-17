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
@Table(name = "racas")
public class RacaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String descricao;
    private String especie;
}
