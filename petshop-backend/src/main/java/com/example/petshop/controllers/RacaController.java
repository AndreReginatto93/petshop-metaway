package com.example.petshop.controllers;

import com.example.petshop.dtos.RacaRecordDto;
import com.example.petshop.entities.RacaEntity;
import com.example.petshop.services.RacaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/racas")
public class RacaController {
    private final RacaService racaService;

    public RacaController(RacaService racaService) {
        this.racaService = racaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RacaEntity> getRacaById(@PathVariable Long id) {
        RacaEntity raca = racaService.getRacaById(id);
        return ResponseEntity.ok(raca);
    }

    @GetMapping
    public ResponseEntity<List<RacaEntity>> getAllRacas() {
        List<RacaEntity> racas = racaService.getAllRacas();
        return ResponseEntity.ok(racas);
    }

    @PostMapping
    public ResponseEntity<RacaEntity> saveRaca(@RequestBody RacaRecordDto racaRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(racaService.saveRaca(racaRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RacaEntity> updateRaca(
            @PathVariable Long id,
            @RequestBody RacaRecordDto racaRecordDto) {
        return racaService.updateRaca(id, racaRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRaca(@PathVariable Long id){
        racaService.deleteRaca(id);
        return ResponseEntity.ok(null);
    }
}
