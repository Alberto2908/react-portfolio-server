package com.alberto.portfolio.controllers;

import com.alberto.portfolio.models.Experiencia;
import com.alberto.portfolio.services.ExperienciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/experiencias")
public class ExperienciaController {

    private final ExperienciaService experienciaService;

    public ExperienciaController(ExperienciaService experienciaService) {
        this.experienciaService = experienciaService;
    }

    @GetMapping
    public List<Experiencia> getAll() {
        return experienciaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experiencia> getById(@PathVariable String id) {
        return experienciaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Experiencia> create(@Valid @RequestBody Experiencia experiencia) {
        Experiencia created = experienciaService.create(experiencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experiencia> update(@PathVariable String id, @Valid @RequestBody Experiencia experiencia) {
        Experiencia updated = experienciaService.update(id, experiencia);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        experienciaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
