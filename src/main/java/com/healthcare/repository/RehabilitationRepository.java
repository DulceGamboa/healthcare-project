package com.healthcare.repository;

import com.healthcare.model.Rehabilitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RehabilitationRepository extends JpaRepository<Rehabilitation, Long> {
}