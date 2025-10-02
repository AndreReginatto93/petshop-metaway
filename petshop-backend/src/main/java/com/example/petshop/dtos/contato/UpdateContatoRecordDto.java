package com.example.petshop.dtos.contato;

import com.example.petshop.entities.contato.ContatoTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateContatoRecordDto(
        String tag,
        ContatoTipo tipo,
        @NotBlank(message = "valor é obrigatório")
        String valor) {
}
