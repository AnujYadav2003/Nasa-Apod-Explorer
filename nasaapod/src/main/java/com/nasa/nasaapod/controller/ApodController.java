package com.nasa.nasaapod.controller;


import com.nasa.nasaapod.model.ApodResponse;
import com.nasa.nasaapod.service.ApodService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/apod")
@Validated
public class ApodController {

    private final ApodService apodService;

    public ApodController(ApodService apodService) {
        this.apodService = apodService;
    }

    @GetMapping("/today")
    public ResponseEntity<ApodResponse> getToday() {
        ApodResponse resp = apodService.getApod(null);
        
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/date")
    public ResponseEntity<ApodResponse> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ApodResponse resp = apodService.getApod(date.toString());
        return ResponseEntity.ok(resp);
    }

    // Example endpoint: GET /api/apod/recent?days=5
    @GetMapping("/recent")
    public ResponseEntity<?> getRecent(@RequestParam(defaultValue = "5") int days) {
        if (days < 1 || days > 30) return ResponseEntity.badRequest().body("days must be between 1 and 30");
        // Naive implementation: call APOD for each day. Could be parallelized and cached per date.
        java.util.List<ApodResponse> list = new java.util.ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < days; i++) {
            LocalDate d = today.minusDays(i);
            ApodResponse r = apodService.getApod(d.toString());
            list.add(r);
        }
        return ResponseEntity.ok(list);
    }
}
