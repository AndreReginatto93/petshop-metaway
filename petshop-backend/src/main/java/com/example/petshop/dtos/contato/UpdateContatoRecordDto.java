package com.example.petshop.dtos.contato;

import com.example.petshop.entities.contato.ContatoTipo;

public record UpdateContatoRecordDto(String tag,
                                     ContatoTipo tipo,
                                     String valor) {
}
