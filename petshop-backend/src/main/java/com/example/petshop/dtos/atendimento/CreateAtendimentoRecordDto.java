package com.example.petshop.dtos.atendimento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateAtendimentoRecordDto(
        @NotNull(message = "Tipo é obrigatório")
        Long petId,
        @NotBlank(message = "descricao é obrigatório")
        String descricao,
        @NotNull(message = "valor é obrigatório")
        BigDecimal valor,
        @NotNull(message = "dataAtendimento é obrigatório")
        LocalDateTime dataAtendimento) {
}
