package com.example.petshop.dtos.pet;

import java.time.LocalDate;

public record CreatePetRecordDto(Long clienteId,
                                 Long racaId,
                                 LocalDate dataNascimento,
                                 String nome) {
}
