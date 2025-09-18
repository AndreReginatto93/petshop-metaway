package com.example.petshop.dtos.pet;

import java.time.LocalDate;

public record UpdatePetRecordDto(Long racaId,
                                 LocalDate dataNascimento,
                                 String nome) {
}
