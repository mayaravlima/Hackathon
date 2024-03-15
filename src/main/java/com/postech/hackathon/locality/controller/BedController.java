package com.postech.hackathon.locality.controller;

import com.postech.hackathon.locality.model.request.BedRequest;
import com.postech.hackathon.locality.model.response.BedResponse;
import com.postech.hackathon.locality.service.BedService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/beds")
@AllArgsConstructor
public class BedController {

    private BedService bedService;

    @PostMapping("{idRoom}")
    public ResponseEntity<BedResponse> createBed(@Valid @RequestBody BedRequest request, @PathVariable Long idRoom) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bedService.createBed(request, idRoom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BedResponse> getBed(@PathVariable Long id) {
        return ResponseEntity.ok(bedService.getBed(id));
    }

    @GetMapping
    public ResponseEntity<List<BedResponse>> getAllBeds() {
        return ResponseEntity.ok(bedService.getAllBeds());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBed(@PathVariable Long id) {
        bedService.deleteBed(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BedResponse> updateBed(@PathVariable Long id, @Valid @RequestBody BedRequest request) {
        return ResponseEntity.ok(bedService.updateBed(id, request));
    }
}
