package com.healthcare.controller;

import com.healthcare.model.Medication;
import com.healthcare.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medications")
@CrossOrigin(origins = "*")
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepository;

    @GetMapping
    public List<Medication> getAll() {
        return medicationRepository.findAll();
    }

    @PostMapping
    public Medication create(@RequestBody Medication medication) {
        return medicationRepository.save(medication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        medicationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}