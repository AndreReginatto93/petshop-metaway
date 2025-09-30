package com.example.petshop.services;


import com.example.petshop.dtos.contato.UpdateContatoRecordDto;
import com.example.petshop.dtos.endereco.CreateEnderecoRecordDto;
import com.example.petshop.dtos.endereco.UpdateEnderecoRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.entities.EnderecoEntity;
import com.example.petshop.entities.contato.ContatoEntity;
import com.example.petshop.repositories.ClienteRepository;
import com.example.petshop.repositories.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, ClienteRepository clienteRepository) {
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<EnderecoEntity> findByClienteLogin(String login) {
        return enderecoRepository.findByClienteLogin(login);
    }

    public EnderecoEntity getEnderecoById(Long id) {
        Optional<EnderecoEntity> enderecoEntity = enderecoRepository.findById(id);

        if (enderecoEntity.isEmpty()) {
            throw new EntityNotFoundException("Endereço não encontrado com id: " + id);
        }

        return enderecoEntity.get();
    }

    public List<EnderecoEntity> getAllEnderecos() {
        return enderecoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public EnderecoEntity saveEndereco(CreateEnderecoRecordDto createEnderecoRecordDto){
        ClienteEntity clienteEntity = clienteRepository.findById(createEnderecoRecordDto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com id: " + createEnderecoRecordDto.clienteId()));

        EnderecoEntity enderecoEntity = EnderecoEntity.builder()
                .cliente(clienteEntity)
                .logradouro(createEnderecoRecordDto.logradouro())
                .cidade(createEnderecoRecordDto.cidade())
                .bairro(createEnderecoRecordDto.bairro())
                .complemento(createEnderecoRecordDto.complemento())
                .tag(createEnderecoRecordDto.tag())
                .build();

        return enderecoRepository.saveAndFlush(enderecoEntity);
    }

    @Transactional
    public Optional<EnderecoEntity> updateEndereco(Long id, UpdateEnderecoRecordDto updateEnderecoRecordDto) {
        return enderecoRepository.findById(id)
                .map(existingEndereco -> {
                    existingEndereco.setLogradouro(updateEnderecoRecordDto.logradouro());
                    existingEndereco.setCidade(updateEnderecoRecordDto.cidade());
                    existingEndereco.setBairro(updateEnderecoRecordDto.bairro());
                    existingEndereco.setComplemento(updateEnderecoRecordDto.complemento());
                    existingEndereco.setTag(updateEnderecoRecordDto.tag());
                    return enderecoRepository.saveAndFlush(existingEndereco);
                });
    }

    @Transactional
    public Optional<EnderecoEntity> updateEnderecoByLogin(Long id, UpdateEnderecoRecordDto updateEnderecoRecordDto, String login) {
        ClienteEntity clienteEntity = clienteRepository.findByCpf(login)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com cpf: " + login));

        clienteEntity.getEnderecos().stream().filter(endereco -> endereco.getId().equals(id)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Contato não encontrado com id: " + id + " para o cliente com cpf: " + login));

        return enderecoRepository.findById(id)
                .map(existingEndereco -> {
                    existingEndereco.setLogradouro(updateEnderecoRecordDto.logradouro());
                    existingEndereco.setCidade(updateEnderecoRecordDto.cidade());
                    existingEndereco.setBairro(updateEnderecoRecordDto.bairro());
                    existingEndereco.setComplemento(updateEnderecoRecordDto.complemento());
                    existingEndereco.setTag(updateEnderecoRecordDto.tag());
                    return enderecoRepository.saveAndFlush(existingEndereco);
                });
    }

    @Transactional
    public void deleteEndereco(Long id){
        enderecoRepository.deleteById(id);
    }
}
