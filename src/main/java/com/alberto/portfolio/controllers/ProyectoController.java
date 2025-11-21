package com.alberto.portfolio.controllers;

import com.alberto.portfolio.models.Proyecto;
import com.alberto.portfolio.services.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public List<Proyecto> getAll() {
        return proyectoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getById(@PathVariable String id) {
        return proyectoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proyecto> create(@Valid @RequestBody Proyecto proyecto) {
        Proyecto created = proyectoService.create(proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> update(@PathVariable String id, @Valid @RequestBody Proyecto proyecto) {
        Proyecto updated = proyectoService.update(id, proyecto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        proyectoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
