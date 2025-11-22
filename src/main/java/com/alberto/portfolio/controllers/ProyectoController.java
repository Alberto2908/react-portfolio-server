package com.alberto.portfolio.controllers;

import com.alberto.portfolio.models.Proyecto;
import com.alberto.portfolio.services.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Proyecto> create(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("enlace") String enlace,
            @RequestParam("tecnologias") List<String> tecnologias,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {
        String imagePath = null;
        if (imagen != null && !imagen.isEmpty()) {
            imagePath = saveImage(imagen);
        }
        Proyecto proyecto = new Proyecto(nombre, descripcion, tecnologias, enlace, imagePath != null ? imagePath : null);
        Proyecto created = proyectoService.create(proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Proyecto> update(
            @PathVariable String id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("enlace") String enlace,
            @RequestParam("tecnologias") List<String> tecnologias,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) throws IOException {
        Proyecto existing = proyectoService.findById(id).orElse(null);
        if (existing == null) return ResponseEntity.notFound().build();

        String imagePath = existing.getImagen();
        if (imagen != null && !imagen.isEmpty()) {
            deleteIfLocalUpload(imagePath);
            imagePath = saveImage(imagen);
        }

        Proyecto payload = new Proyecto(id, nombre, descripcion, tecnologias, imagePath, enlace);
        Proyecto updated = proyectoService.update(id, payload);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        proyectoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadDir = Paths.get("uploads", "proyectos");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String original = file.getOriginalFilename();
        String sanitized = original == null ? "image" : original.replaceAll("[^a-zA-Z0-9._-]", "_");
        String filename = System.currentTimeMillis() + "_" + sanitized;
        Path target = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/proyectos/" + filename;
    }

    private void deleteIfLocalUpload(String imagePath) {
        if (imagePath == null) return;
        if (!imagePath.startsWith("/uploads/proyectos/")) return;
        try {
            Path target = Paths.get("uploads", "proyectos", imagePath.replace("/uploads/proyectos/", ""));
            Files.deleteIfExists(target);
        } catch (Exception ignored) { }
    }
}
