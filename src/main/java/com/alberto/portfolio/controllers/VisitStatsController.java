package com.alberto.portfolio.controllers;

import com.alberto.portfolio.models.VisitStats;
import com.alberto.portfolio.services.VisitStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visits")
@CrossOrigin(origins = "http://localhost:5173")
public class VisitStatsController {

    private final VisitStatsService service;

    public VisitStatsController(VisitStatsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<VisitStats> get() {
        return ResponseEntity.ok(service.get());
    }

    @PostMapping("/increment")
    public ResponseEntity<VisitStats> increment() {
        return ResponseEntity.ok(service.increment());
    }
}
