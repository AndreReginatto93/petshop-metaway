package com.example.petshop.controllers;

import com.example.petshop.dtos.cliente.CreateClienteRecordDto;
import com.example.petshop.dtos.cliente.UpdateClienteRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/me")
    public ResponseEntity<ClienteEntity> getOwnCliente(@AuthenticationPrincipal UserDetails userDetails) {
        ClienteEntity cliente = clienteService.findByCpf(userDetails.getUsername());

        return ResponseEntity.ok(cliente);
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

    @PutMapping("/me")
    public ResponseEntity<ClienteEntity> updateOwnCliente(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestBody UpdateClienteRecordDto updateClienteRecordDto) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.updateClienteByCpf(userDetails.getUsername(), updateClienteRecordDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCliente(@PathVariable Long id){
        clienteService.deleteCliente(id);
        return ResponseEntity.ok(null);
    }
}
