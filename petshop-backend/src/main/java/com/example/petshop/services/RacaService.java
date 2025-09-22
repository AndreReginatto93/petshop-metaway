package com.example.petshop.services;

import com.example.petshop.dtos.RacaRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.entities.RacaEntity;
import com.example.petshop.repositories.RacaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RacaService {
    private final RacaRepository racaRepository;

    public RacaService(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public RacaEntity getRacaById(Long id) {
        Optional<RacaEntity> racaEntity = racaRepository.findById(id);

        if (racaEntity.isEmpty()) {
            throw new EntityNotFoundException("Raça não encontrado com id: " + id);
        }

        return racaEntity.get();
    }

    public List<RacaEntity> getAllRacas() {
        return racaRepository.findAll();
    }

    @Transactional
    public RacaEntity saveRaca(RacaRecordDto racaRecordDto){
        RacaEntity racaEntity = RacaEntity.builder()
                .descricao(racaRecordDto.descricao())
                .especie(racaRecordDto.especie())
                .build();

        return racaRepository.saveAndFlush(racaEntity);
    }

    @Transactional
    public Optional<RacaEntity> updateRaca(Long id, RacaRecordDto racaRecordDto) {
        return racaRepository.findById(id)
                .map(existingRaca -> {
                    existingRaca.setDescricao(racaRecordDto.descricao());
                    existingRaca.setEspecie(racaRecordDto.especie());
                    return racaRepository.saveAndFlush(existingRaca);
                });
    }

    @Transactional
    public void deleteRaca(Long id){
        racaRepository.deleteById(id);
    }
}
