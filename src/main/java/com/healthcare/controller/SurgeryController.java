package com.healthcare.controller;

import com.healthcare.model.Surgery;
import com.healthcare.service.SurgeryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surgeries")
@CrossOrigin
public class SurgeryController {

    private final SurgeryService surgeryService;

    public SurgeryController(SurgeryService surgeryService) {
        this.surgeryService = surgeryService;
    }

    @GetMapping
    public List<Surgery> getAllSurgeries() {
        return surgeryService.getAllSurgeries();
    }

    @PostMapping
    public Surgery createSurgery(@RequestBody Surgery surgery) {
        return surgeryService.createSurgery(surgery);
    }

    @DeleteMapping("/{id}")
    public void deleteSurgery(@PathVariable Long id) {
        surgeryService.deleteSurgery(id);
    }
}