package com.healthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "surgeries")
public class Surgery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: surgeries.user_id -> users.id
    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"email", "age"})
    private User user;

    @Column(name = "surgery_type", length = 100)
    private String surgeryType;

    @Column(name = "surgery_date")
    private LocalDate surgeryDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public Surgery() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getSurgeryType() { return surgeryType; }
    public void setSurgeryType(String surgeryType) { this.surgeryType = surgeryType; }

    public LocalDate getSurgeryDate() { return surgeryDate; }
    public void setSurgeryDate(LocalDate surgeryDate) { this.surgeryDate = surgeryDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}