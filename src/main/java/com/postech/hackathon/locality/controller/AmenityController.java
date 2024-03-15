package com.postech.hackathon.locality.controller;

import com.postech.hackathon.locality.model.request.AmenityRequest;
import com.postech.hackathon.locality.model.response.AmenityResponse;
import com.postech.hackathon.locality.service.AmenityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/amenities")
@AllArgsConstructor
public class AmenityController {

    private AmenityService amenityService;

    @PostMapping("/{idLocality}")
    public ResponseEntity<AmenityResponse> createAmenity(@Valid @RequestBody AmenityRequest request, @PathVariable Long idLocality) {
        return ResponseEntity.status(HttpStatus.CREATED).body(amenityService.createAmenity(request, idLocality));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityResponse> getAmenity(@PathVariable Long id) {
        return ResponseEntity.ok(amenityService.getAmenity(id));
    }

    @GetMapping
    public ResponseEntity<List<AmenityResponse>> getAllAmenities() {
        return ResponseEntity.ok(amenityService.getAllAmenities());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmenityResponse> updateAmenity(@PathVariable Long id, @Valid @RequestBody AmenityRequest request) {
        return ResponseEntity.ok(amenityService.updateAmenity(id, request));
    }


}
