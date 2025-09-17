package com.example.petshop.entities.contato;

import com.example.petshop.entities.ClienteEntity;
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
@Table(name = "contatos")
public class ContatoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private ClienteEntity cliente;
    private String tag;
    private ContatoTipo tipo;
    private String valor;
}
