package com.healthcare.controller;

import com.healthcare.model.Appointment;
import com.healthcare.service.AppointmentService;
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
}