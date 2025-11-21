package com.alberto.portfolio.controllers;

import com.alberto.portfolio.models.ContactaConmigo;
import com.alberto.portfolio.services.ContactaConmigoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/contacto")
public class ContactaConmigoController {

    private final ContactaConmigoService contactaConmigoService;

    public ContactaConmigoController(ContactaConmigoService contactaConmigoService) {
        this.contactaConmigoService = contactaConmigoService;
    }

    @GetMapping
    public List<ContactaConmigo> getAll() {
        return contactaConmigoService.findAll();
    }

    @PostMapping
    public ResponseEntity<ContactaConmigo> create(@Valid @RequestBody ContactaConmigo contactaConmigo) {
        ContactaConmigo created = contactaConmigoService.create(contactaConmigo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        contactaConmigoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
