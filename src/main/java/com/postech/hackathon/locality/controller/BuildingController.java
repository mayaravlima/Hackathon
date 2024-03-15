package com.postech.hackathon.locality.controller;

import com.postech.hackathon.locality.model.request.BuildingRequest;
import com.postech.hackathon.locality.model.response.BuildingResponse;
import com.postech.hackathon.locality.service.BuildingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/buildings")
@AllArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping("/{idLocality}")
    public ResponseEntity<BuildingResponse> createBuilding(@RequestBody BuildingRequest buildingRequest, @PathVariable Long idLocality) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingService.createBuilding(buildingRequest, idLocality));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponse> getBuilding(@PathVariable Long id) {
        return ResponseEntity.status(200).body(buildingService.getBuilding(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllBuildings() {
        return ResponseEntity.status(200).body(buildingService.getAllBuildings());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildingResponse> updateBuilding(@PathVariable Long id, @RequestBody BuildingRequest request) {
        return ResponseEntity.ok(buildingService.updateBuilding(id, request));
    }

}
