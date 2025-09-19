package com.example.petshop.controllers;

import com.example.petshop.dtos.atendimento.CreateAtendimentoRecordDto;
import com.example.petshop.dtos.atendimento.UpdateAtendimentoRecordDto;
import com.example.petshop.entities.AtendimentoEntity;
import com.example.petshop.services.AtendimentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/atendimentos")
public class AtendimentoController {
    private final AtendimentoService atendimentoService;

    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AtendimentoEntity>> getAtendimentoById(@PathVariable Long id) {
        Optional<AtendimentoEntity> atendimento = atendimentoService.getAtendimentoById(id);
        if (atendimento.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atendimento);
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoEntity>> getAllAtendimentos() {
        List<AtendimentoEntity> atendimentos = atendimentoService.getAllAtendimentos();
        return ResponseEntity.ok(atendimentos);
    }

    @PostMapping
    public ResponseEntity saveAtendimento(@RequestBody CreateAtendimentoRecordDto createAtendimentoRecordDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoService.saveAtendimento(createAtendimentoRecordDto));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Pet not found")) {
                Map<String, String> errorBody = Map.of(
                        "error", "Unprocessable Entity",
                        "message", "Pet informado não existe"
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
    public ResponseEntity<AtendimentoEntity> updateAtendimento(
            @PathVariable Long id,
            @RequestBody UpdateAtendimentoRecordDto updateAtendimentoRecordDto) {
        return atendimentoService.updateAtendimento(id, updateAtendimentoRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAtendimento(@PathVariable Long id){
        Optional<AtendimentoEntity> atendimento = atendimentoService.getAtendimentoById(id);
        if (atendimento.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        atendimentoService.deleteAtendimento(id);
        return ResponseEntity.ok(null);
    }
}
