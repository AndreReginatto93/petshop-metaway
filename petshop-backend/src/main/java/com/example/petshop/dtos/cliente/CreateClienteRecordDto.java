package com.example.petshop.dtos.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record CreateClienteRecordDto(
        @NotBlank(message = "nome é obrigatório")
        String nome,
        @NotBlank(message = "cpf é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 números")
        String cpf,
        @NotNull(message = "dataCadastro é obrigatório")
        LocalDateTime dataCadastro) {
}
