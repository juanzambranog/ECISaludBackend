package eci.cvds.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;
    private String fullName;
    private String cedula;
    private String email;
    private LocalDate appointmentDate;
    private String specialtyId;
    private String specialtyName;
    private String doctor;
    private String location;
    private AppointmentStatus status;

    public enum AppointmentStatus {
        CONFIRMED,
        REJECTED,
        CANCELLED
    }
}
