package com.example.petshop.dtos.cliente;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UpdateClienteRecordDto(
        @NotBlank(message = "nome é obrigatório")
        String nome
) {
}
