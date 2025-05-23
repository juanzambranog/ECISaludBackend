package eci.cvds.demo.service;

import eci.cvds.demo.model.Appointment;
import eci.cvds.demo.model.Specialty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    
    private final List<Appointment> appointments = new ArrayList<>();
    private long nextId = 1;
    
    @Autowired
    private SpecialtyService specialtyService;

    public Appointment scheduleAppointment(Appointment appointment) {
        // Validate appointment date
        if (appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            appointment.setStatus(Appointment.AppointmentStatus.REJECTED);
            appointment.setId(String.valueOf(nextId++));
            appointments.add(appointment);
            return appointment;
        }

        // Validate specialty exists
        Optional<Specialty> specialty = specialtyService.getSpecialtyById(appointment.getSpecialtyId());
        if (specialty.isEmpty()) {
            appointment.setStatus(Appointment.AppointmentStatus.REJECTED);
            appointment.setId(String.valueOf(nextId++));
            appointments.add(appointment);
            return appointment;
        }

        // Set specialty details
        Specialty spec = specialty.get();
        appointment.setSpecialtyName(spec.getName());
        appointment.setDoctor(spec.getDoctor());
        appointment.setLocation(spec.getLocation());

        // Set as confirmed if all validations pass
        appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
        appointment.setId(String.valueOf(nextId++));
        appointments.add(appointment);
        return appointment;
    }

    public List<Appointment> getAppointmentsByEmail(String email) {
        return appointments.stream()
                .filter(a -> a.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByEmailAndStatus(String email, Appointment.AppointmentStatus status) {
        return appointments.stream()
                .filter(a -> a.getEmail().equals(email) && a.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Optional<Appointment> cancelAppointment(String appointmentId) {
        Optional<Appointment> appointment = appointments.stream()
                .filter(a -> a.getId().equals(appointmentId))
                .findFirst();
                
        if (appointment.isPresent()) {
            Appointment app = appointment.get();
            app.setStatus(Appointment.AppointmentStatus.CANCELLED);
            return Optional.of(app);
        }
        return Optional.empty();
    }

    public boolean validateAppointmentData(Appointment appointment) {
        return appointment.getFullName() != null && !appointment.getFullName().trim().isEmpty() &&
               appointment.getCedula() != null && !appointment.getCedula().trim().isEmpty() &&
               appointment.getEmail() != null && !appointment.getEmail().trim().isEmpty() &&
               appointment.getAppointmentDate() != null &&
               appointment.getSpecialtyId() != null && !appointment.getSpecialtyId().trim().isEmpty();
    }
}
