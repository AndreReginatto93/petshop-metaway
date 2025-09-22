package com.example.petshop.controllers;

import com.example.petshop.dtos.pet.CreatePetRecordDto;
import com.example.petshop.dtos.pet.UpdatePetRecordDto;
import com.example.petshop.entities.PetEntity;
import com.example.petshop.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<PetEntity>> getOwnPets(@AuthenticationPrincipal UserDetails userDetails) {
        List<PetEntity> pets = petService.findByClienteLogin(userDetails.getUsername());

        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetEntity> getPetById(@PathVariable Long id) {
        PetEntity pet = petService.getPetById(id);
        return ResponseEntity.ok(pet);
    }

    @GetMapping
    public ResponseEntity<List<PetEntity>> getAllPets() {
        List<PetEntity> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @PostMapping
    public ResponseEntity savePet(@RequestBody CreatePetRecordDto petRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.savePet(petRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePet(@PathVariable Long id,
                                    @RequestBody UpdatePetRecordDto petRecordDto) {
        return petService.updatePet(id, petRecordDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PutMapping("/me/{id}")
    public ResponseEntity updatePetByLogin(@PathVariable Long id,
                                           @RequestBody UpdatePetRecordDto petRecordDto,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return petService.updateContatoByLogin(id, petRecordDto, userDetails.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePet(@PathVariable Long id){
        petService.deletePet(id);
        return ResponseEntity.ok(null);
    }
}
