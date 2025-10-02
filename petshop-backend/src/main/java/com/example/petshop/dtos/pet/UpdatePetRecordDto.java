package com.example.petshop.dtos.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdatePetRecordDto(
        @NotNull(message = "racaId é obrigatório")
        Long racaId,
        @NotNull(message = "dataNascimento é obrigatório")
        LocalDate dataNascimento,
        @NotBlank(message = "nome é obrigatório")
        String nome) {
}
