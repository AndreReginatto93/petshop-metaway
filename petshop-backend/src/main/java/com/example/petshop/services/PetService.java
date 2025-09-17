package com.example.petshop.services;

import com.example.petshop.entities.PetEntity;
import com.example.petshop.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Optional<PetEntity> getPetById(String id) {
        return petRepository.findById(id);
    }

    public List<PetEntity> getAllPets() {
        return petRepository.findAll();
    }
}
