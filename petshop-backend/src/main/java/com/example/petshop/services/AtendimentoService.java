package com.example.petshop.services;

import com.example.petshop.dtos.atendimento.CreateAtendimentoRecordDto;
import com.example.petshop.dtos.atendimento.UpdateAtendimentoRecordDto;
import com.example.petshop.entities.AtendimentoEntity;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.entities.PetEntity;
import com.example.petshop.repositories.AtendimentoRepository;
import com.example.petshop.repositories.ClienteRepository;
import com.example.petshop.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {
    private final AtendimentoRepository atendimentoRepository;
    private final PetRepository petRepository;
    private final ClienteRepository clienteRepository;
    
    public AtendimentoService(AtendimentoRepository atendimentoRepository, PetRepository petRepository, ClienteRepository clienteRepository) {
        this.atendimentoRepository = atendimentoRepository;
        this.petRepository = petRepository;
        this.clienteRepository = clienteRepository;

    }

    public List<AtendimentoEntity> findByClienteLogin(String login) {
        return atendimentoRepository.findByClienteLogin(login);
    }

    public List<AtendimentoEntity> findByPet(Long petId, UserDetails userDetails) {
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            return atendimentoRepository.findByPet(petId);
        }

        ClienteEntity clienteEntity = clienteRepository.findByCpf(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com cpf: " + userDetails.getUsername()));

        clienteEntity.getPets().stream().filter(pet -> pet.getId().equals(petId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com id: " + petId + " para o cliente com cpf: " + userDetails.getUsername()));

        return atendimentoRepository.findByPet(petId);
    }

    public AtendimentoEntity getAtendimentoById(Long id) {
        Optional<AtendimentoEntity> atendimentoEntity = atendimentoRepository.findById(id);

        if (atendimentoEntity.isEmpty()) {
            throw new EntityNotFoundException("Atendimento não encontrado com id: " + id);
        }

        return atendimentoEntity.get();

    }
    public List<AtendimentoEntity> getAllAtendimentos() {
        return atendimentoRepository.findAll();
    }

    @Transactional
    public AtendimentoEntity saveAtendimento(CreateAtendimentoRecordDto createAtendimentoRecordDto){
        PetEntity petEntity = petRepository.findById(createAtendimentoRecordDto.petId())
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com id: " + createAtendimentoRecordDto.petId()));

        AtendimentoEntity atendimentoEntity = AtendimentoEntity.builder()
                .dataAtendimento(createAtendimentoRecordDto.dataAtendimento())
                .descricao(createAtendimentoRecordDto.descricao())
                .valor(createAtendimentoRecordDto.valor())
                .pet(petEntity)
                .build();

        return atendimentoRepository.saveAndFlush(atendimentoEntity);
    }

    @Transactional
    public Optional<AtendimentoEntity> updateAtendimento(Long id, UpdateAtendimentoRecordDto updateAtendimentoRecordDto) {
        return atendimentoRepository.findById(id)
                .map(existingAtendimento -> {
                    existingAtendimento.setDataAtendimento(updateAtendimentoRecordDto.dataAtendimento());
                    existingAtendimento.setDescricao(updateAtendimentoRecordDto.descricao());
                    existingAtendimento.setValor(updateAtendimentoRecordDto.valor());
                    return atendimentoRepository.saveAndFlush(existingAtendimento);
                });
    }

    @Transactional
    public void deleteAtendimento(Long id){
        atendimentoRepository.deleteById(id);
    }
}
