package com.example.petshop.services;

import com.example.petshop.dtos.pet.CreatePetRecordDto;
import com.example.petshop.dtos.pet.UpdatePetRecordDto;
import com.example.petshop.entities.ClienteEntity;
import com.example.petshop.entities.PetEntity;
import com.example.petshop.entities.RacaEntity;
import com.example.petshop.repositories.ClienteRepository;
import com.example.petshop.repositories.PetRepository;
import com.example.petshop.repositories.RacaRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public List<PetEntity> findByClienteLogin(String login) {
        return petRepository.findByClienteLogin(login);
    }

    public PetEntity getPetById(Long id) {
        Optional<PetEntity> petEntity = petRepository.findById(id);

        if (petEntity.isEmpty()) {
            throw new EntityNotFoundException("Pet não encontrado com id: " + id);
        }

        return petEntity.get();
    }

    public List<PetEntity> getAllPets() {
        return petRepository.findAll();
    }

    @Transactional
    public PetEntity savePet(CreatePetRecordDto createPetRecordDto){
        RacaEntity racaEntity = racaRepository.findById(createPetRecordDto.racaId())
                .orElseThrow(() -> new EntityNotFoundException("Raca não encontrado com id: " + createPetRecordDto.racaId()));

        ClienteEntity clienteEntity = clienteRepository.findById(createPetRecordDto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com id: " + createPetRecordDto.clienteId()));

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
                .orElseThrow(() -> new EntityNotFoundException("Raca não encontrado com id: " + updatePetRecordDto.racaId()));
        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setNome(updatePetRecordDto.nome());
                    existingPet.setDataNascimento(updatePetRecordDto.dataNascimento());
                    existingPet.setRaca(racaEntity);

                    return petRepository.saveAndFlush(existingPet);
                });
    }

    @Transactional
    public Optional<PetEntity> updateContatoByLogin(Long id, UpdatePetRecordDto updatePetRecordDto, String login) {
        ClienteEntity clienteEntity = clienteRepository.findByCpf(login)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com cpf: " + login));

        clienteEntity.getPets().stream().filter(pet -> pet.getId().equals(id)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com id: " + id + " para o cliente com cpf: " + login));

        RacaEntity racaEntity = racaRepository.findById(updatePetRecordDto.racaId())
                .orElseThrow(() -> new EntityNotFoundException("Raça não encontrado com id: " + updatePetRecordDto.racaId()));

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
