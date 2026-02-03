package com.alberto.portfolio.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LegacyCvRedirectController {

    // Backward compatibility: if someone hits the old static path, redirect to the new endpoint
    @GetMapping("/uploads/cv/cv.pdf")
    public ResponseEntity<Void> redirectLegacyCvPath() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/cv/view");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
