package com.alberto.portfolio.controllers;

import com.alberto.portfolio.models.Formacion;
import com.alberto.portfolio.services.FormacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/formaciones")
public class FormacionController {

    private final FormacionService formacionService;

    public FormacionController(FormacionService formacionService) {
        this.formacionService = formacionService;
    }

    @GetMapping
    public List<Formacion> getAll() {
        return formacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formacion> getById(@PathVariable String id) {
        return formacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Formacion> create(@Valid @RequestBody Formacion formacion) {
        Formacion created = formacionService.create(formacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formacion> update(@PathVariable String id, @Valid @RequestBody Formacion formacion) {
        Formacion updated = formacionService.update(id, formacion);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        formacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
