package com.postech.hackathon.locality.controller;

import com.postech.hackathon.locality.model.request.FacilityRequest;
import com.postech.hackathon.locality.model.response.FacilityResponse;
import com.postech.hackathon.locality.service.FacilityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/facilities")
@AllArgsConstructor
public class FacilityController {

    private FacilityService facilityService;

    @PostMapping("{idBathroom}")
    public ResponseEntity<FacilityResponse> createFacility(@Valid @RequestBody FacilityRequest request, @PathVariable Long idBathroom) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facilityService.createFacility(request, idBathroom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacilityResponse> getFacility(@PathVariable Long id) {
        return ResponseEntity.ok(facilityService.getFacility(id));
    }

    @GetMapping
    public ResponseEntity<List<FacilityResponse>> getAllFacilities() {
        return ResponseEntity.ok(facilityService.getAllFacilities());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacilityResponse> updateFacility(@PathVariable Long id, @Valid @RequestBody FacilityRequest request) {
        return ResponseEntity.ok(facilityService.updateFacility(id, request));
    }

}
