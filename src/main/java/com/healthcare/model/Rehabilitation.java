package com.healthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "rehabilitation")
public class Rehabilitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: rehabilitation.user_id -> users.id
    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"email", "age"})
    private User user;

    @Column(name = "therapy_type", length = 100)
    private String therapyType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String progress;

    public Rehabilitation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTherapyType() { return therapyType; }
    public void setTherapyType(String therapyType) { this.therapyType = therapyType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getProgress() { return progress; }
    public void setProgress(String progress) { this.progress = progress; }
}