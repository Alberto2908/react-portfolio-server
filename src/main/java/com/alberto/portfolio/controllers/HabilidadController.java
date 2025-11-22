package com.alberto.portfolio.controllers;

import com.alberto.portfolio.models.Habilidad;
import com.alberto.portfolio.services.HabilidadService;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Habilidad> create(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            imagePath = saveImage(image);
        }
        Habilidad habilidad = new Habilidad(null, name, imagePath != null ? imagePath : "", category);
        Habilidad created = habilidadService.create(habilidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Habilidad> update(
            @PathVariable String id,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        Habilidad existing = habilidadService.findById(id).orElse(null);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        String imagePath = existing.getImage();
        if (image != null && !image.isEmpty()) {
            // delete previous if stored in uploads
            deleteIfLocalUpload(imagePath);
            imagePath = saveImage(image);
        }

        Habilidad payload = new Habilidad(id, name, imagePath != null ? imagePath : "", category);
        Habilidad updated = habilidadService.update(id, payload);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        habilidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadDir = Paths.get("uploads", "habilidades");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String original = file.getOriginalFilename();
        String sanitized = original == null ? "image" : original.replaceAll("[^a-zA-Z0-9._-]", "_");
        String filename = System.currentTimeMillis() + "_" + sanitized;
        Path target = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/habilidades/" + filename;
    }

    private void deleteIfLocalUpload(String imagePath) {
        if (imagePath == null) return;
        if (!imagePath.startsWith("/uploads/habilidades/")) return;
        try {
            Path target = Paths.get("uploads", "habilidades", imagePath.replace("/uploads/habilidades/", ""));
            Files.deleteIfExists(target);
        } catch (Exception ignored) { }
    }
}
