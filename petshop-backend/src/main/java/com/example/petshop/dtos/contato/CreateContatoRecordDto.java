package com.example.petshop.dtos.contato;

import com.example.petshop.entities.contato.ContatoTipo;

public record CreateContatoRecordDto(Long clienteId,
                                     String tag,
                                     ContatoTipo tipo,
                                     String valor) {
}
