package com.healthcare.service;

import com.healthcare.model.Rehabilitation;
import com.healthcare.repository.RehabilitationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RehabilitationService {

    private final RehabilitationRepository rehabilitationRepository;

    public RehabilitationService(RehabilitationRepository rehabilitationRepository) {
        this.rehabilitationRepository = rehabilitationRepository;
    }

    public List<Rehabilitation> getAllRehabilitations() {
        return rehabilitationRepository.findAll();
    }

    public Rehabilitation getRehabilitationById(Long id) {
        return rehabilitationRepository.findById(id).orElse(null);
    }

    public Rehabilitation createRehabilitation(Rehabilitation rehabilitation) {
        return rehabilitationRepository.save(rehabilitation);
    }

    public void deleteRehabilitation(Long id) {
        rehabilitationRepository.deleteById(id);
    }
}