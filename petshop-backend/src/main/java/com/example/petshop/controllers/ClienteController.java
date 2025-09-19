package com.example.petshop.controllers;

import com.example.petshop.dtos.cliente.CreateClienteRecordDto;
import com.example.petshop.dtos.cliente.UpdateClienteRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> getClienteById(@PathVariable Long id) {
        ClienteEntity cliente = clienteService.getClienteById(id);

        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> getAllClientes() {
        List<ClienteEntity> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteEntity> saveCliente(@RequestBody CreateClienteRecordDto createClienteRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.saveCliente(createClienteRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteEntity> updateCliente(
            @PathVariable Long id,
            @RequestBody UpdateClienteRecordDto updateClienteRecordDto) {
        return clienteService.updateCliente(id, updateClienteRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCliente(@PathVariable Long id){
        Optional<ClienteEntity> cliente = clienteService.getClienteById(id);
        if (cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        clienteService.deleteCliente(id);
        return ResponseEntity.ok(null);
    }
}
