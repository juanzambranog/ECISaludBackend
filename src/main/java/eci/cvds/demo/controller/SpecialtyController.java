package eci.cvds.demo.controller;

import eci.cvds.demo.model.Specialty;
import eci.cvds.demo.service.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
@Tag(name = "Specialty Controller", description = "Endpoints for managing medical specialties")
@CrossOrigin(origins = "*")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    @Operation(summary = "Get all specialties", description = "Returns a list of all available medical specialties")
    public ResponseEntity<List<Specialty>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.getAllSpecialties());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get specialty by ID", description = "Returns a specific specialty by its ID")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable String id) {
        return specialtyService.getSpecialtyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/initialize")
    @Operation(summary = "Initialize default specialties", description = "Creates default specialties if none exist")
    public ResponseEntity<Void> initializeSpecialties() {
        specialtyService.initializeDefaultSpecialties();
        return ResponseEntity.ok().build();
    }
}
