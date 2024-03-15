package com.postech.hackathon.locality.controller;

import com.postech.hackathon.locality.model.request.LocalityRequest;
import com.postech.hackathon.locality.model.response.LocalityResponse;
import com.postech.hackathon.locality.service.LocalityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/localities")
@AllArgsConstructor
public class LocalityController {

    private LocalityService service;

    @PostMapping
    public ResponseEntity<LocalityResponse> createLocality(@Valid @RequestBody LocalityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createLocality(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalityResponse> getLocalityById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLocalityById(id));
    }

    @GetMapping
    public ResponseEntity<List<LocalityResponse>> getAllLocalities() {
        return ResponseEntity.ok(service.getAllLocalities());
    }
}
