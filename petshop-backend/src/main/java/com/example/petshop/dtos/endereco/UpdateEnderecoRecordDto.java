package com.example.petshop.dtos.endereco;

import com.example.petshop.entities.contato.ContatoTipo;
import jakarta.validation.constraints.NotBlank;

public record UpdateEnderecoRecordDto(
        @NotBlank(message = "logradouro é obrigatório")
        String logradouro,
        @NotBlank(message = "cidade é obrigatório")
        String cidade,
        @NotBlank(message = "bairro é obrigatório")
        String bairro,
        String complemento,
        String tag) {
}
