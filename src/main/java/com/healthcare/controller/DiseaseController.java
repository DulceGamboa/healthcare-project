package com.healthcare.controller;

import com.healthcare.model.Disease;
import com.healthcare.service.DiseaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diseases")
@CrossOrigin
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @GetMapping
    public List<Disease> getAllDiseases() {
        return diseaseService.getAllDiseases();
    }

    @GetMapping("/{id}")
    public Disease getDiseaseById(@PathVariable Long id) {
        return diseaseService.getDiseaseById(id);
    }

    @PostMapping
    public Disease createDisease(@RequestBody Disease disease) {
        return diseaseService.createDisease(disease);
    }

    @PutMapping("/{id}")
    public Disease updateDisease(@PathVariable Long id, @RequestBody Disease disease) {
        return diseaseService.updateDisease(id, disease);
    }

    @DeleteMapping("/{id}")
    public void deleteDisease(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
    }
}