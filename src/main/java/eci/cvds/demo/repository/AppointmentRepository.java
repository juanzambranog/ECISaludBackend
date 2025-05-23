package eci.cvds.demo.repository;

import eci.cvds.demo.model.Appointment;
import eci.cvds.demo.model.Appointment.AppointmentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByEmail(String email);
    List<Appointment> findByEmailAndStatus(String email, AppointmentStatus status);
}
