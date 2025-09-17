package com.example.petshop.services;

import com.example.petshop.dtos.cliente.CreateClienteRecordDto;
import com.example.petshop.dtos.cliente.UpdateClienteRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<ClienteEntity> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public List<ClienteEntity> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public ClienteEntity saveCliente(CreateClienteRecordDto createClienteRecordDto){
        ClienteEntity clienteEntity = new ClienteEntity();

        clienteEntity.setNome(createClienteRecordDto.nome());
        clienteEntity.setCpf(createClienteRecordDto.cpf());
        clienteEntity.setDataCadastro(createClienteRecordDto.dataCadastro());

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
    public void deleteCliente(Long id){
        clienteRepository.deleteById(id);
    }
}
