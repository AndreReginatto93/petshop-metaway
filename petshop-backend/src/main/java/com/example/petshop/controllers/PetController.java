package com.example.petshop.controllers;

import com.example.petshop.dtos.RacaRecordDto;
import com.example.petshop.dtos.pet.CreatePetRecordDto;
import com.example.petshop.dtos.pet.UpdatePetRecordDto;
import com.example.petshop.entities.PetEntity;
import com.example.petshop.entities.RacaEntity;
import com.example.petshop.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PetEntity>> getPetById(@PathVariable Long id) {
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

    @PostMapping
    public ResponseEntity savePet(@RequestBody CreatePetRecordDto petRecordDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(petService.savePet(petRecordDto));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Raca not found")) {
                Map<String, String> errorBody = Map.of(
                        "error", "Unprocessable Entity",
                        "message", "Raçaa informada não existe"
                );
                return ResponseEntity.unprocessableEntity().body(errorBody);
            }
            if (e.getMessage().contains("Cliente not found")) {
                Map<String, String> errorBody = Map.of(
                        "error", "Unprocessable Entity",
                        "message", "Cliente informado não existe"
                );
                return ResponseEntity.unprocessableEntity().body(errorBody);
            }

            // fallback para outros IllegalArgumentException
            Map<String, String> errorBody = Map.of(
                    "error", "Bad Request",
                    "message", e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorBody);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePet(
            @PathVariable Long id,
            @RequestBody UpdatePetRecordDto petRecordDto) {
        try {

            return petService.updatePet(id, petRecordDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
        if (e.getMessage().contains("Raca not found")) {
            Map<String, String> errorBody = Map.of(
                    "error", "Unprocessable Entity",
                    "message", "Raça informada não existe"
            );
            return ResponseEntity.unprocessableEntity().body(errorBody);
        }

        // fallback para outros IllegalArgumentException
        Map<String, String> errorBody = Map.of(
                "error", "Bad Request",
                "message", e.getMessage()
        );
        return ResponseEntity.badRequest().body(errorBody);
    }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePet(@PathVariable Long id){
        Optional<PetEntity> pet = petService.getPetById(id);
        if (pet.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        petService.deletePet(id);
        return ResponseEntity.ok(null);
    }
}
