package com.example.petshop.services;

import com.example.petshop.dtos.cliente.CreateClienteRecordDto;
import com.example.petshop.dtos.cliente.UpdateClienteRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteEntity findByCpf(String cpf) {
        Optional<ClienteEntity> clienteEntity = clienteRepository.findByCpf(cpf);

        if (clienteEntity.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado com cpf: " + cpf);
        }

        return clienteEntity.get();
    }

    public ClienteEntity getClienteById(Long id) {
        Optional<ClienteEntity> clienteEntity = clienteRepository.findById(id);

        if (clienteEntity.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado com id: " + id);
        }

        return clienteEntity.get();
    }

    public List<ClienteEntity> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public ClienteEntity saveCliente(CreateClienteRecordDto createClienteRecordDto){
        ClienteEntity clienteEntity = ClienteEntity.builder()
                .nome(createClienteRecordDto.nome())
                .cpf(createClienteRecordDto.cpf())
                .dataCadastro(createClienteRecordDto.dataCadastro())
                .build();

        return clienteRepository.saveAndFlush(clienteEntity);
    }

    @Transactional
    public Optional<ClienteEntity> updateCliente(Long id, UpdateClienteRecordDto updateClienteRecordDto) {
        return clienteRepository.findById(id)
                .map(existingCliente -> {
                    existingCliente.setNome(updateClienteRecordDto.nome());
                    return clienteRepository.saveAndFlush(existingCliente);
                });
    }

    @Transactional
    public ClienteEntity updateClienteByCpf(String cpf, UpdateClienteRecordDto updateClienteRecordDto) {
        Optional<ClienteEntity> existingCliente =  clienteRepository.findByCpf(cpf);

        if (existingCliente.isEmpty()){
            throw new EntityNotFoundException("Cliente não encontrado com cpf: " + cpf);
        }

        ClienteEntity cliente = existingCliente.get();

        cliente.setNome(updateClienteRecordDto.nome());

        return clienteRepository.saveAndFlush(cliente);
    }

    @Transactional
    public void deleteCliente(Long id){
        clienteRepository.deleteById(id);
    }
}
