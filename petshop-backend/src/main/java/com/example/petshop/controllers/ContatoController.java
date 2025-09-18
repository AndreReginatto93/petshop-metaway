package com.example.petshop.controllers;

import com.example.petshop.dtos.contato.CreateContatoRecordDto;
import com.example.petshop.dtos.contato.UpdateContatoRecordDto;
import com.example.petshop.entities.contato.ContatoEntity;
import com.example.petshop.entities.contato.ContatoTipo;
import com.example.petshop.services.ContatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/contatos")
public class ContatoController {
    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ContatoEntity>> getContatoById(@PathVariable Long id) {
        Optional<ContatoEntity> contato = contatoService.getContatoById(id);
        if (contato.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contato);
    }

    @GetMapping
    public ResponseEntity<List<ContatoEntity>> getAllContatos() {
        List<ContatoEntity> contatos = contatoService.getAllContatos();
        return ResponseEntity.ok(contatos);
    }

    @PostMapping
    public ResponseEntity saveContato(@RequestBody CreateContatoRecordDto createContatoRecordDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(contatoService.saveContato(createContatoRecordDto));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Cliente not found")) {
                Map<String, String> errorBody = Map.of(
                        "error", "Unprocessable Entity",
                        "message", "Cliente informado não existe"
                );
                return ResponseEntity.unprocessableEntity().body(errorBody);
            }

            // fallback para outros IllegalArgumentException
            Map<String, String> errorBody = Map.of(
                    "error", "Bad Request",
                    "message", e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorBody);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoEntity> updateContato(
            @PathVariable Long id,
            @RequestBody UpdateContatoRecordDto updateContatoRecordDto) {
        return contatoService.updateContato(id, updateContatoRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteContato(@PathVariable Long id){
        Optional<ContatoEntity> contato = contatoService.getContatoById(id);
        if (contato.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        contatoService.deleteContato(id);
        return ResponseEntity.ok(null);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        if (e.getMessage().contains("ContatoTipo")) {
            Map<String, String> errorBody = Map.of(
                    "error", "Unprocessable Entity",
                    "message", "tipo deve ser: "+Arrays.toString(ContatoTipo.values())
            );
            return ResponseEntity.unprocessableEntity().body(errorBody);
        }
        Map<String, String> errorBody = Map.of(
                "error", "Bad Request",
                "message", "Erro de leitura do corpo da requisição. Verifique os campos enviados."
        );
        return ResponseEntity.badRequest().body(errorBody);
    }
}
