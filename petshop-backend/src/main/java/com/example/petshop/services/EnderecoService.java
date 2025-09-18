package com.example.petshop.services;


import com.example.petshop.dtos.endereco.CreateEnderecoRecordDto;
import com.example.petshop.dtos.endereco.UpdateEnderecoRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.entities.EnderecoEntity;
import com.example.petshop.repositories.ClienteRepository;
import com.example.petshop.repositories.EnderecoRepository;
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

    public Optional<EnderecoEntity> getEnderecoById(Long id) {
        return enderecoRepository.findById(id);
    }

    public List<EnderecoEntity> getAllEnderecos() {
        return enderecoRepository.findAll();
    }

    @Transactional
    public EnderecoEntity saveEndereco(CreateEnderecoRecordDto createEnderecoRecordDto){
        EnderecoEntity enderecoEntity = new EnderecoEntity();

        ClienteEntity clienteEntity = clienteRepository.findById(createEnderecoRecordDto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente not found with id: " + createEnderecoRecordDto.clienteId()));

        enderecoEntity.setCliente(clienteEntity);
        enderecoEntity.setLogradouro(createEnderecoRecordDto.logradouro());
        enderecoEntity.setCidade(createEnderecoRecordDto.cidade());
        enderecoEntity.setBairro(createEnderecoRecordDto.bairro());
        enderecoEntity.setComplemento(createEnderecoRecordDto.complemento());
        enderecoEntity.setTag(createEnderecoRecordDto.tag());

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
    public void deleteEndereco(Long id){
        enderecoRepository.deleteById(id);
    }
}
