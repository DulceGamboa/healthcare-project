package com.healthcare.controller;

import com.healthcare.model.Medication;
import com.healthcare.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controller for managing patient medications
@RestController
@RequestMapping("/api/medications")
@CrossOrigin(origins = "*")
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepository;

    // Returns all medications from the database
    @GetMapping
    public List<Medication> getAll() {
        return medicationRepository.findAll();
    }

    // Creates a new medication record linked to a patient
    @PostMapping
    public Medication create(@RequestBody Medication medication) {
        return medicationRepository.save(medication);
    }

    // Deletes a medication record by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        medicationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}