package com.example.petshop.controllers;

import com.example.petshop.entities.PetEntity;
import com.example.petshop.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PetEntity>> getPetById(@PathVariable String id) {
        Optional<PetEntity> pet = petService.getPetById(id);
        if (pet.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @GetMapping
    public ResponseEntity<List<PetEntity>> getAllPets() {
        List<PetEntity> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }


}
