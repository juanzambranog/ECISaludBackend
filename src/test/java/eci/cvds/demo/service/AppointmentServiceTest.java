package eci.cvds.demo.service;

import eci.cvds.demo.model.Appointment;
import eci.cvds.demo.model.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    private AppointmentService appointmentService;
    private SpecialtyService specialtyService;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialtyService();
        appointmentService = new AppointmentService();
        ReflectionTestUtils.setField(appointmentService, "specialtyService", specialtyService);

        // Initialize test data
        Specialty specialty = new Specialty("1", "Medicina General", "Descripción", "Dr. Juan", "Consultorio 101", "imagen.jpg");
        specialtyService.createSpecialty(specialty);

        appointment = new Appointment();
        appointment.setFullName("Juan Perez");
        appointment.setCedula("123456789");
        appointment.setEmail("juan@example.com");
        appointment.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment.setSpecialtyId("1");
    }

    @Test
    void scheduleAppointment_WithValidData_ShouldConfirm() {
        Appointment result = appointmentService.scheduleAppointment(appointment);

        assertEquals(Appointment.AppointmentStatus.CONFIRMED, result.getStatus());
        assertNotNull(result.getId());
    }

    @Test
    void scheduleAppointment_WithPastDate_ShouldReject() {
        appointment.setAppointmentDate(LocalDate.now().minusDays(1));

        Appointment result = appointmentService.scheduleAppointment(appointment);

        assertEquals(Appointment.AppointmentStatus.REJECTED, result.getStatus());
    }

    @Test
    void scheduleAppointment_WithInvalidSpecialty_ShouldReject() {
        appointment.setSpecialtyId("999");

        Appointment result = appointmentService.scheduleAppointment(appointment);

        assertEquals(Appointment.AppointmentStatus.REJECTED, result.getStatus());
    }

    @Test
    void getAppointmentsByEmail_ShouldReturnList() {
        appointmentService.scheduleAppointment(appointment);

        List<Appointment> result = appointmentService.getAppointmentsByEmail("juan@example.com");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("juan@example.com", result.get(0).getEmail());
    }

    @Test
    void getAppointmentsByEmailAndStatus_ShouldReturnFilteredList() {
        appointmentService.scheduleAppointment(appointment);

        List<Appointment> result = appointmentService.getAppointmentsByEmailAndStatus(
                "juan@example.com", Appointment.AppointmentStatus.CONFIRMED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Appointment.AppointmentStatus.CONFIRMED, result.get(0).getStatus());
    }

    @Test
    void cancelAppointment_WhenExists_ShouldCancel() {
        Appointment scheduled = appointmentService.scheduleAppointment(appointment);

        Optional<Appointment> result = appointmentService.cancelAppointment(scheduled.getId());

        assertTrue(result.isPresent());
        assertEquals(Appointment.AppointmentStatus.CANCELLED, result.get().getStatus());
    }

    @Test
    void cancelAppointment_WhenNotExists_ShouldReturnEmpty() {
        Optional<Appointment> result = appointmentService.cancelAppointment("999");

        assertFalse(result.isPresent());
    }

    @Test
    void validateAppointmentData_WithValidData_ShouldReturnTrue() {
        assertTrue(appointmentService.validateAppointmentData(appointment));
    }

    @Test
    void validateAppointmentData_WithInvalidData_ShouldReturnFalse() {
        appointment.setFullName("");
        assertFalse(appointmentService.validateAppointmentData(appointment));

        appointment.setFullName("Juan");
        appointment.setCedula("");
        assertFalse(appointmentService.validateAppointmentData(appointment));

        appointment.setCedula("123");
        appointment.setEmail("");
        assertFalse(appointmentService.validateAppointmentData(appointment));

        appointment.setEmail("juan@example.com");
        appointment.setAppointmentDate(null);
        assertFalse(appointmentService.validateAppointmentData(appointment));
    }
}
