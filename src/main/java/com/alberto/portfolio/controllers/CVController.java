package com.alberto.portfolio.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/cv")
@CrossOrigin(origins = "http://localhost:5173")
public class CVController {

    private static final Path UPLOAD_DIR = Paths.get(System.getenv().getOrDefault("UPLOADS_DIR", "uploads"), "cv");
    private static final String TARGET_FILENAME = "cv.pdf";
    private static final String PUBLIC_DOWNLOAD_NAME = "Alberto Cabello Lasheras.pdf";

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

    @GetMapping(value = "/download")
    public ResponseEntity<Resource> downloadCv() {
        try {
            if (!Files.exists(UPLOAD_DIR)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Prefer a file named exactly as the public name, else fallback to cv.pdf, else any .pdf
            Path preferred = UPLOAD_DIR.resolve(PUBLIC_DOWNLOAD_NAME);
            Path fallback = UPLOAD_DIR.resolve(TARGET_FILENAME);
            Path toServe = null;

            if (Files.exists(preferred)) {
                toServe = preferred;
            } else if (Files.exists(fallback)) {
                toServe = fallback;
            } else {
                try (Stream<Path> files = Files.list(UPLOAD_DIR)) {
                    Optional<Path> anyPdf = files
                        .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".pdf"))
                        .findFirst();
                    if (anyPdf.isPresent()) {
                        toServe = anyPdf.get();
                    }
                }
            }

            if (toServe == null || !Files.exists(toServe)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Resource resource = new UrlResource(toServe.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment()
                .filename(PUBLIC_DOWNLOAD_NAME, StandardCharsets.UTF_8)
                .build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
