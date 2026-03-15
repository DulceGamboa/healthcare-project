package com.healthcare.controller;

import com.healthcare.model.Surgery;
import com.healthcare.service.SurgeryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller for managing surgical procedures
@RestController
@RequestMapping("/api/surgeries")
@CrossOrigin
public class SurgeryController {

    private final SurgeryService surgeryService;

    public SurgeryController(SurgeryService surgeryService) {
        this.surgeryService = surgeryService;
    }

    // Returns all surgeries from the database
    @GetMapping
    public List<Surgery> getAllSurgeries() {
        return surgeryService.getAllSurgeries();
    }


    // Creates a new surgery record linked to a patient
    @PostMapping
    public Surgery createSurgery(@RequestBody Surgery surgery) {
        return surgeryService.createSurgery(surgery);
    }

    // Deletes a surgery record by its ID
    @DeleteMapping("/{id}")
    public void deleteSurgery(@PathVariable Long id) {
        surgeryService.deleteSurgery(id);
    }
}