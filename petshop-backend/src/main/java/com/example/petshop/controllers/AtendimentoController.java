package com.example.petshop.controllers;

import com.example.petshop.dtos.atendimento.CreateAtendimentoRecordDto;
import com.example.petshop.dtos.atendimento.UpdateAtendimentoRecordDto;
import com.example.petshop.entities.AtendimentoEntity;
import com.example.petshop.services.AtendimentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/atendimentos")
public class AtendimentoController {
    private final AtendimentoService atendimentoService;

    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<AtendimentoEntity>> getAllAtendimentosByLogin(@AuthenticationPrincipal UserDetails userDetails) {
        List<AtendimentoEntity> atendimentos = atendimentoService.findByClienteLogin(userDetails.getUsername());
        return ResponseEntity.ok(atendimentos);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<AtendimentoEntity>> getAllAtendimentosByPet(@PathVariable Long petId,
                                                                           @AuthenticationPrincipal UserDetails userDetails) {
        List<AtendimentoEntity> atendimentos = atendimentoService.findByPet(petId, userDetails);
        return ResponseEntity.ok(atendimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoEntity> getAtendimentoById(@PathVariable Long id) {
        AtendimentoEntity atendimento = atendimentoService.getAtendimentoById(id);

        return ResponseEntity.ok(atendimento);
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoEntity>> getAllAtendimentos() {
        List<AtendimentoEntity> atendimentos = atendimentoService.getAllAtendimentos();
        return ResponseEntity.ok(atendimentos);
    }

    @PostMapping
    public ResponseEntity saveAtendimento(@RequestBody CreateAtendimentoRecordDto createAtendimentoRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoService.saveAtendimento(createAtendimentoRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoEntity> updateAtendimento(
            @PathVariable Long id,
            @RequestBody UpdateAtendimentoRecordDto updateAtendimentoRecordDto) {
        return atendimentoService.updateAtendimento(id, updateAtendimentoRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAtendimento(@PathVariable Long id){
        atendimentoService.deleteAtendimento(id);
        return ResponseEntity.ok(null);
    }
}
