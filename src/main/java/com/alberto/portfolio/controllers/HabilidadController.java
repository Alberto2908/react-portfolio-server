package com.alberto.portfolio.controllers;

import com.alberto.portfolio.models.Habilidad;
import com.alberto.portfolio.services.HabilidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/habilidades")
public class HabilidadController {

    private final HabilidadService habilidadService;

    public HabilidadController(HabilidadService habilidadService) {
        this.habilidadService = habilidadService;
    }

    @GetMapping
    public List<Habilidad> getAll() {
        return habilidadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habilidad> getById(@PathVariable String id) {
        return habilidadService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Habilidad> create(@Valid @RequestBody Habilidad habilidad) {
        Habilidad created = habilidadService.create(habilidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habilidad> update(@PathVariable String id, @Valid @RequestBody Habilidad habilidad) {
        Habilidad updated = habilidadService.update(id, habilidad);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        habilidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
