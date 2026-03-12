package com.healthcare.service;

import com.healthcare.model.Disease;
import com.healthcare.repository.DiseaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    // Retrieve all diseases
    public List<Disease> getAllDiseases() {
        return diseaseRepository.findAll();
    }

    // Retrieve a disease by ID
    public Disease getDiseaseById(Long id) {
        return diseaseRepository.findById(id).orElse(null);
    }

    // Create a new disease
    public Disease createDisease(Disease disease) {
        return diseaseRepository.save(disease);
    }

    // Update an existing disease
    public Disease updateDisease(Long id, Disease updated) {
        Disease disease = getDiseaseById(id);
        if (disease != null) {
            disease.setDiseaseName(updated.getDiseaseName());
            disease.setUser(updated.getUser());
            return diseaseRepository.save(disease);
        }
        return null;
    }

    // Delete a disease by ID
    public void deleteDisease(Long id) {
        diseaseRepository.deleteById(id);
    }
}
