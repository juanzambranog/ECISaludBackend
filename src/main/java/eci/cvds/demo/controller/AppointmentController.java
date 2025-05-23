package eci.cvds.demo.controller;

import eci.cvds.demo.model.Appointment;
import eci.cvds.demo.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointment Controller", description = "Endpoints for managing medical appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Schedule appointment", description = "Creates a new medical appointment with validation")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestBody Appointment appointment) {
        if (!appointmentService.validateAppointmentData(appointment)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(appointmentService.scheduleAppointment(appointment));
    }

    @GetMapping
    @Operation(summary = "Get appointments by email", description = "Returns all appointments for a specific email")
    public ResponseEntity<List<Appointment>> getAppointmentsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByEmail(email));
    }

    @GetMapping("/filter")
    @Operation(summary = "Get appointments by email and status", description = "Returns filtered appointments by email and status")
    public ResponseEntity<List<Appointment>> getAppointmentsByEmailAndStatus(
            @RequestParam String email,
            @RequestParam Appointment.AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByEmailAndStatus(email, status));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel appointment", description = "Cancels an existing appointment")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable String id) {
        return appointmentService.cancelAppointment(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
