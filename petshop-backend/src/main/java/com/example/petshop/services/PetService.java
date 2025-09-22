package com.example.petshop.services;

import com.example.petshop.dtos.pet.CreatePetRecordDto;
import com.example.petshop.dtos.pet.UpdatePetRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.entities.PetEntity;
import com.example.petshop.entities.RacaEntity;
import com.example.petshop.repositories.ClienteRepository;
import com.example.petshop.repositories.PetRepository;
import com.example.petshop.repositories.RacaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final RacaRepository racaRepository;
    private final ClienteRepository clienteRepository;

    public PetService(PetRepository petRepository, RacaRepository racaRepository, ClienteRepository clienteRepository) {
        this.petRepository = petRepository;
        this.racaRepository = racaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Optional<PetEntity> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public List<PetEntity> getAllPets() {
        return petRepository.findAll();
    }

    @Transactional
    public PetEntity savePet(CreatePetRecordDto createPetRecordDto){
        RacaEntity racaEntity = racaRepository.findById(createPetRecordDto.racaId())
                .orElseThrow(() -> new IllegalArgumentException("Raca not found with id: " + createPetRecordDto.racaId()));

        ClienteEntity clienteEntity = clienteRepository.findById(createPetRecordDto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente not found with id: " + createPetRecordDto.clienteId()));

        PetEntity petEntity = PetEntity.builder()
                .nome(createPetRecordDto.nome())
                .dataNascimento(createPetRecordDto.dataNascimento())
                .raca(racaEntity)
                .cliente(clienteEntity)
                .build();

        return petRepository.save(petEntity);
    }

    @Transactional
    public Optional<PetEntity> updatePet(Long id, UpdatePetRecordDto updatePetRecordDto) {
        RacaEntity racaEntity = racaRepository.findById(updatePetRecordDto.racaId())
                .orElseThrow(() -> new IllegalArgumentException("Raca not found with id: " + updatePetRecordDto.racaId()));
        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setNome(updatePetRecordDto.nome());
                    existingPet.setDataNascimento(updatePetRecordDto.dataNascimento());
                    existingPet.setRaca(racaEntity);

                    return petRepository.saveAndFlush(existingPet);
                });
    }

    @Transactional
    public void deletePet(Long id){
        petRepository.deleteById(id);
    }

}
