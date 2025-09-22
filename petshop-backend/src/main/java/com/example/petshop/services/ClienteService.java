package com.example.petshop.services;

import com.example.petshop.dtos.cliente.CreateClienteRecordDto;
import com.example.petshop.dtos.cliente.UpdateClienteRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final AuthService authService;

    public ClienteService(ClienteRepository clienteRepository, AuthService authService) {
        this.clienteRepository = clienteRepository;
        this.authService = authService;
    }

    public ClienteEntity getClienteById(Long id) {

        Optional<ClienteEntity> clienteEntity = clienteRepository.findById(id);

        // Verifica se o usuário autenticado é o dono do recurso ou um ADMIN
        boolean isOwner = authService.getAuthenticatedUsername().equals(clienteEntity.map(ClienteEntity::getCpf).orElse(null));
        boolean isAdmin = authService.getAuthenticatedUser().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Você não tem permissão para acessar este cliente");
        }

        if (clienteEntity.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }

        return clienteEntity.get();
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
                    // Verifica se o usuário autenticado é o dono do recurso ou um ADMIN
                    boolean isOwner = authService.getAuthenticatedUsername().equals(existingCliente.getCpf());
                    boolean isAdmin = authService.getAuthenticatedUser().getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                    if (!isOwner && !isAdmin) {
                        throw new AccessDeniedException("Você não tem permissão para alterar este cliente");
                    }

                    existingCliente.setNome(updateClienteRecordDto.nome());
                    return clienteRepository.saveAndFlush(existingCliente);
                });
    }

    @Transactional
    public void deleteCliente(Long id){
        clienteRepository.deleteById(id);
    }
}
