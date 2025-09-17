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
@Table(name = "pets")
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private RacaEntity raca;
    private String dataNascimento;
    private String nome;
}
