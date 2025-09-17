package com.example.petshop.entities.contato;

public enum ContatoTipo {
    EMAIL("email"),
    TELEFONE("telefone");

    private String tipo;

    ContatoTipo(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }
}
