package com.healthcare.controller;

import com.healthcare.model.Rehabilitation;
import com.healthcare.service.RehabilitationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rehabilitations")
@CrossOrigin
public class RehabilitationController {

    private final RehabilitationService rehabilitationService;

    public RehabilitationController(RehabilitationService rehabilitationService) {
        this.rehabilitationService = rehabilitationService;
    }

    @GetMapping
    public List<Rehabilitation> getAllRehabilitations() {
        return rehabilitationService.getAllRehabilitations();
    }

    @PostMapping
    public Rehabilitation createRehabilitation(@RequestBody Rehabilitation rehabilitation) {
        return rehabilitationService.createRehabilitation(rehabilitation);
    }

    @DeleteMapping("/{id}")
    public void deleteRehabilitation(@PathVariable Long id) {
        rehabilitationService.deleteRehabilitation(id);
    }
}