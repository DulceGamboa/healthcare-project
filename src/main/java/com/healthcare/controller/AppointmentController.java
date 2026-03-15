package com.healthcare.controller;

import com.healthcare.model.Appointment;
import com.healthcare.service.AppointmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller for managing medical appointments
@RestController
@RequestMapping("/api/appointments")
@CrossOrigin
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Returns all appointments from the database
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Creates a new appointment linked to a patient
    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    // Deletes an appointment by its ID
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

// Updates the status of an appointment to Completed
@PutMapping("/{id}")
public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Appointment appointment) {
    return appointmentService.getAllAppointments()
        .stream()
        .filter(a -> a.getId().equals(id))
        .findFirst()
        .map(existing -> {
            existing.setStatus(appointment.getStatus());
            return ResponseEntity.ok(appointmentService.createAppointment(existing));
        })
        .orElse(ResponseEntity.notFound().build());
}

@PostMapping("/{id}/complete")
public ResponseEntity<?> complete(@PathVariable Long id) {
    Appointment existing = appointmentService.getAppointmentById(id);
    if (existing == null) return ResponseEntity.notFound().build();
    existing.setStatus("Completada");
    appointmentService.createAppointment(existing);
    return ResponseEntity.ok(existing);
}
}