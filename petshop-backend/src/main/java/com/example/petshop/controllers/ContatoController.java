package com.example.petshop.controllers;

import com.example.petshop.dtos.contato.CreateContatoRecordDto;
import com.example.petshop.dtos.contato.UpdateContatoRecordDto;
import com.example.petshop.entities.contato.ContatoEntity;
import com.example.petshop.entities.contato.ContatoTipo;
import com.example.petshop.services.ContatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/contatos")
public class ContatoController {
    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<ContatoEntity>> getOwnContatos(@AuthenticationPrincipal UserDetails userDetails) {
        List<ContatoEntity> contatos = contatoService.findByClienteLogin(userDetails.getUsername());
        return ResponseEntity.ok(contatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoEntity> getContatoById(@PathVariable Long id) {
        ContatoEntity contato = contatoService.getContatoById(id);

        return ResponseEntity.ok(contato);
    }

    @GetMapping
    public ResponseEntity<List<ContatoEntity>> getAllContatos() {
        List<ContatoEntity> contatos = contatoService.getAllContatos();
        return ResponseEntity.ok(contatos);
    }

    @PostMapping
    public ResponseEntity saveContato(@RequestBody CreateContatoRecordDto createContatoRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contatoService.saveContato(createContatoRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoEntity> updateContato(
            @PathVariable Long id,
            @RequestBody UpdateContatoRecordDto updateContatoRecordDto) {
        return contatoService.updateContato(id, updateContatoRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/me/{id}")
    public ResponseEntity<ContatoEntity> updateContatoByLogin(
            @PathVariable Long id,
            @RequestBody UpdateContatoRecordDto updateContatoRecordDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return contatoService.updateContatoByLogin(id, updateContatoRecordDto, userDetails.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteContato(@PathVariable Long id){
        contatoService.deleteContato(id);
        return ResponseEntity.ok(null);
    }
}
