package com.example.petshop.dtos.endereco;

import com.example.petshop.entities.contato.ContatoTipo;

public record CreateEnderecoRecordDto(Long clienteId,
                                      String logradouro,
                                      String cidade,
                                      String bairro,
                                      String complemento,
                                      String tag) {
}
