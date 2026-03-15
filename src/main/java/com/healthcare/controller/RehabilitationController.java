package com.healthcare.controller;

import com.healthcare.model.Rehabilitation;
import com.healthcare.service.RehabilitationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller for managing rehabilitation therapies
@RestController
@RequestMapping("/api/rehabilitations")
@CrossOrigin
public class RehabilitationController {

    private final RehabilitationService rehabilitationService;

    public RehabilitationController(RehabilitationService rehabilitationService) {
        this.rehabilitationService = rehabilitationService;
    }

     // Returns all rehabilitation records from the database
    @GetMapping
    public List<Rehabilitation> getAllRehabilitations() {
        return rehabilitationService.getAllRehabilitations();
    }

    // Creates a new rehabilitation therapy linked to a patient
    @PostMapping
    public Rehabilitation createRehabilitation(@RequestBody Rehabilitation rehabilitation) {
        return rehabilitationService.createRehabilitation(rehabilitation);
    }

    // Deletes a rehabilitation record by its ID
    @DeleteMapping("/{id}")
    public void deleteRehabilitation(@PathVariable Long id) {
        rehabilitationService.deleteRehabilitation(id);
    }
}