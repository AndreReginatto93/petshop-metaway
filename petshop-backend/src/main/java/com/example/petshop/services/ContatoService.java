package com.example.petshop.services;

import com.example.petshop.dtos.contato.CreateContatoRecordDto;
import com.example.petshop.dtos.contato.UpdateContatoRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.entities.contato.ContatoEntity;
import com.example.petshop.repositories.ClienteRepository;
import com.example.petshop.repositories.ContatoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final ClienteRepository clienteRepository;

    public ContatoService(ContatoRepository contatoRepository, ClienteRepository clienteRepository) {
        this.contatoRepository = contatoRepository;
        this.clienteRepository = clienteRepository;
    }


    public List<ContatoEntity> findByClienteLogin(String login) {
        return contatoRepository.findByClienteLogin(login);
    }

    public ContatoEntity getContatoById(Long id) {
        Optional<ContatoEntity> contatoEntity = contatoRepository.findById(id);

        if (contatoEntity.isEmpty()) {
            throw new EntityNotFoundException("Contato não encontrado com id: " + id);
        }

        return contatoEntity.get();
    }

    public List<ContatoEntity> getAllContatos() {
        return contatoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public ContatoEntity saveContato(CreateContatoRecordDto createContatoRecordDto){
        ClienteEntity clienteEntity = clienteRepository.findById(createContatoRecordDto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com id: " + createContatoRecordDto.clienteId()));

        ContatoEntity contatoEntity = ContatoEntity.builder()
                .cliente(clienteEntity)
                .tipo(createContatoRecordDto.tipo())
                .valor(createContatoRecordDto.valor())
                .tag(createContatoRecordDto.tag())
                .build();

        return contatoRepository.saveAndFlush(contatoEntity);
    }

    @Transactional
    public Optional<ContatoEntity> updateContato(Long id, UpdateContatoRecordDto updateContatoRecordDto) {
        return contatoRepository.findById(id)
                .map(existingContato -> {
                    existingContato.setTipo(updateContatoRecordDto.tipo());
                    existingContato.setValor(updateContatoRecordDto.valor());
                    existingContato.setTag(updateContatoRecordDto.tag());
                    return contatoRepository.saveAndFlush(existingContato);
                });
    }

    @Transactional
    public Optional<ContatoEntity> updateContatoByLogin(Long id, UpdateContatoRecordDto updateContatoRecordDto, String login) {
        ClienteEntity clienteEntity = clienteRepository.findByCpf(login)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com cpf: " + login));

        clienteEntity.getContatos().stream().filter(contato -> contato.getId().equals(id)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Contato não encontrado com id: " + id + " para o cliente com cpf: " + login));

        Optional<ContatoEntity> contato = contatoRepository.findById(id)
                .map(existingContato -> {
                    existingContato.setTipo(updateContatoRecordDto.tipo());
                    existingContato.setValor(updateContatoRecordDto.valor());
                    existingContato.setTag(updateContatoRecordDto.tag());
                    return contatoRepository.saveAndFlush(existingContato);
                });

        return contato;
    }

    @Transactional
    public void deleteContato(Long id){
        contatoRepository.deleteById(id);
    }
}
