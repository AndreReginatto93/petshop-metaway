package com.example.petshop.services;

import com.example.petshop.dtos.atendimento.CreateAtendimentoRecordDto;
import com.example.petshop.dtos.atendimento.UpdateAtendimentoRecordDto;
import com.example.petshop.entities.AtendimentoEntity;
import com.example.petshop.entities.PetEntity;
import com.example.petshop.repositories.AtendimentoRepository;
import com.example.petshop.repositories.PetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {
    private final AtendimentoRepository atendimentoRepository;
    private final PetRepository petRepository;
    
    public AtendimentoService(AtendimentoRepository atendimentoRepository, PetRepository petRepository) {
        this.atendimentoRepository = atendimentoRepository;
        this.petRepository = petRepository;

    }

    public Optional<AtendimentoEntity> getAtendimentoById(Long id) {
        return atendimentoRepository.findById(id);

    }
    public List<AtendimentoEntity> getAllAtendimentos() {
        return atendimentoRepository.findAll();
    }

    @Transactional
    public AtendimentoEntity saveAtendimento(CreateAtendimentoRecordDto createAtendimentoRecordDto){
        PetEntity petEntity = petRepository.findById(createAtendimentoRecordDto.petId())
                .orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + createAtendimentoRecordDto.petId()));

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
