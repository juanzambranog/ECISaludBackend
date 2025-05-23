package eci.cvds.demo.service;

import eci.cvds.demo.model.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceTest {

    private SpecialtyService specialtyService;
    private Specialty specialty;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialtyService();
        specialty = new Specialty("1", "Medicina General", "Descripción", "Dr. Juan", "Consultorio 101", "imagen.jpg");
    }

    @Test
    void getAllSpecialties_ShouldReturnList() {
        specialtyService.createSpecialty(specialty);
        List<Specialty> result = specialtyService.getAllSpecialties();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getSpecialtyById_WhenExists_ShouldReturnSpecialty() {
        specialtyService.createSpecialty(specialty);
        Optional<Specialty> result = specialtyService.getSpecialtyById("1");

        assertTrue(result.isPresent());
        assertEquals(specialty.getName(), result.get().getName());
    }

    @Test
    void getSpecialtyById_WhenNotExists_ShouldReturnEmpty() {
        Optional<Specialty> result = specialtyService.getSpecialtyById("999");

        assertFalse(result.isPresent());
    }

    @Test
    void initializeDefaultSpecialties_WhenEmpty_ShouldCreateSpecialties() {
        specialtyService.initializeDefaultSpecialties();
        List<Specialty> specialties = specialtyService.getAllSpecialties();

        assertFalse(specialties.isEmpty());
        assertEquals(4, specialties.size());
    }

    @Test
    void initializeDefaultSpecialties_WhenNotEmpty_ShouldNotCreateSpecialties() {
        specialtyService.createSpecialty(specialty);
        specialtyService.initializeDefaultSpecialties();
        List<Specialty> specialties = specialtyService.getAllSpecialties();

        assertEquals(1, specialties.size());
    }
}
