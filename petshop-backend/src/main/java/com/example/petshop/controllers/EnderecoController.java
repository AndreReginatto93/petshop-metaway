package com.example.petshop.controllers;

import com.example.petshop.dtos.endereco.CreateEnderecoRecordDto;
import com.example.petshop.dtos.endereco.UpdateEnderecoRecordDto;
import com.example.petshop.entities.EnderecoEntity;
import com.example.petshop.services.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoEntity> getEnderecoById(@PathVariable Long id) {
        EnderecoEntity endereco = enderecoService.getEnderecoById(id);

        return ResponseEntity.ok(endereco);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoEntity>> getAllEnderecos() {
        List<EnderecoEntity> enderecos = enderecoService.getAllEnderecos();
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    public ResponseEntity saveEndereco(@RequestBody CreateEnderecoRecordDto createEnderecoRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.saveEndereco(createEnderecoRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoEntity> updateEndereco(
            @PathVariable Long id,
            @RequestBody UpdateEnderecoRecordDto updateEnderecoRecordDto) {
        return enderecoService.updateEndereco(id, updateEnderecoRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEndereco(@PathVariable Long id){
        enderecoService.deleteEndereco(id);
        return ResponseEntity.ok(null);
    }
}
