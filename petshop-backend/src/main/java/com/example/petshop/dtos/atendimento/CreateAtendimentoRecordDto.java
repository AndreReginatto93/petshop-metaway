package com.example.petshop.dtos.atendimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateAtendimentoRecordDto(Long petId,
                                         String descricao,
                                         BigDecimal valor,
                                         LocalDateTime dataAtendimento) {
}
