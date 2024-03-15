package com.postech.hackathon.locality.controller;

import com.postech.hackathon.locality.model.request.FurnitureRequest;
import com.postech.hackathon.locality.model.response.FurnitureResponse;
import com.postech.hackathon.locality.service.FurnitureService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/furnitures")
@AllArgsConstructor
public class FurnitureController {

    private FurnitureService furnitureService;

    @PostMapping("{idRoom}")
    public ResponseEntity<FurnitureResponse> createFurniture(@Valid @RequestBody FurnitureRequest request, @PathVariable Long idRoom) {
        return ResponseEntity.status(HttpStatus.CREATED).body(furnitureService.createFurniture(request, idRoom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureResponse> getFurniture(@PathVariable Long id) {
        return ResponseEntity.ok(furnitureService.getFurniture(id));
    }

    @GetMapping
    public ResponseEntity<java.util.List<FurnitureResponse>> getAllFurnitures() {
        return ResponseEntity.ok(furnitureService.getAllFurnitures());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFurniture(@PathVariable Long id) {
        furnitureService.deleteFurniture(id);
        return ResponseEntity.noContent().build();
    }
}
