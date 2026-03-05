package com.healthcare.service;

import com.healthcare.model.Surgery;
import com.healthcare.repository.SurgeryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurgeryService {

    private final SurgeryRepository surgeryRepository;

    public SurgeryService(SurgeryRepository surgeryRepository) {
        this.surgeryRepository = surgeryRepository;
    }

    public List<Surgery> getAllSurgeries() {
        return surgeryRepository.findAll();
    }

    public Surgery getSurgeryById(Long id) {
        return surgeryRepository.findById(id).orElse(null);
    }

    public Surgery createSurgery(Surgery surgery) {
        return surgeryRepository.save(surgery);
    }

    public void deleteSurgery(Long id) {
        surgeryRepository.deleteById(id);
    }
}