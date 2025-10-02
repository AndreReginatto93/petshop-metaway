package com.example.petshop.dtos.endereco;

import com.example.petshop.entities.contato.ContatoTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEnderecoRecordDto(
        @NotNull(message = "clienteId é obrigatório")
        Long clienteId,
        @NotBlank(message = "logradouro é obrigatório")
        String logradouro,
        @NotBlank(message = "cidade é obrigatório")
        String cidade,
        @NotBlank(message = "bairro é obrigatório")
        String bairro,
        String complemento,
        String tag) {
}
