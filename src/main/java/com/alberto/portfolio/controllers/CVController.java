package com.alberto.portfolio.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/cv")
@CrossOrigin(origins = "http://localhost:5173")
public class CVController {

    private static final Path UPLOAD_DIR = Paths.get(System.getenv().getOrDefault("UPLOADS_DIR", "uploads"), "cv");
    private static final String TARGET_FILENAME = "cv.pdf";

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCv(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Archivo vacío");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equalsIgnoreCase("application/pdf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solo se permite PDF");
        }
        try {
            if (!Files.exists(UPLOAD_DIR)) {
                Files.createDirectories(UPLOAD_DIR);
            }
            Path target = UPLOAD_DIR.resolve(TARGET_FILENAME);
            Files.write(target, file.getBytes());
            String publicPath = "/uploads/cv/" + TARGET_FILENAME;
            return ResponseEntity.ok(publicPath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo guardar el CV");
        }
    }
}
