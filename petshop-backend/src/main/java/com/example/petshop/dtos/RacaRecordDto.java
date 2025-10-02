package com.example.petshop.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RacaRecordDto(
        @NotBlank(message = "racaId é obrigatório")
        String descricao,
        @NotBlank(message = "especie é obrigatório")
        String especie) {
}
