package com.example.petshop.repositories;

import com.example.petshop.entities.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PetRepository extends JpaRepository<PetEntity, Long> {
}
