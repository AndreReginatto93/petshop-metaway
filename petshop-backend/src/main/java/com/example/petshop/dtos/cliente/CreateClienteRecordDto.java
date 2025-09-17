package com.example.petshop.dtos.cliente;

import java.time.LocalDateTime;

public record CreateClienteRecordDto(String nome,
                                     String cpf,
                                     LocalDateTime dataCadastro) {
}
