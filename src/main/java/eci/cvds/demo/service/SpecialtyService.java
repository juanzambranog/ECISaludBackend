package eci.cvds.demo.service;

import eci.cvds.demo.model.Specialty;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class SpecialtyService {
    
    private final List<Specialty> specialties = new ArrayList<>();
    private long nextId = 1;

    public List<Specialty> getAllSpecialties() {
        return new ArrayList<>(specialties);
    }

    public Optional<Specialty> getSpecialtyById(String id) {
        return specialties.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public Specialty createSpecialty(Specialty specialty) {
        specialty.setId(String.valueOf(nextId++));
        specialties.add(specialty);
        return specialty;
    }

    public void initializeDefaultSpecialties() {
        if (specialties.isEmpty()) {
            List<Specialty> defaultSpecialties = List.of(
                new Specialty("1", "Medicina General", "Atención médica general y preventiva", "Dr. Juan Pérez", "Consultorio 101", "/images/medicina-general.jpg"),
                new Specialty("2", "Psicología", "Atención en salud mental", "Dra. María García", "Consultorio 102", "/images/psicologia.jpg"),
                new Specialty("3", "Ortopedia", "Tratamiento de problemas musculoesqueléticos", "Dr. Carlos Rodríguez", "Consultorio 103", "/images/ortopedia.jpg"),
                new Specialty("4", "Odontología", "Cuidado dental integral", "Dra. Ana López", "Consultorio 104", "/images/odontologia.jpg")
            );
            specialties.addAll(defaultSpecialties);
            nextId = 5;
        }
    }
}
