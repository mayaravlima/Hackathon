package com.postech.hackathon.optionalService.controller;

import com.postech.hackathon.optionalService.model.OfferedServiceRequest;
import com.postech.hackathon.optionalService.model.OfferedServiceResponse;
import com.postech.hackathon.optionalService.service.OfferedServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/services")
@AllArgsConstructor
public class OfferedServiceController {
    private final OfferedServiceService offeredServiceService;


    @PostMapping
    public ResponseEntity<OfferedServiceResponse> createService(@Valid @RequestBody OfferedServiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offeredServiceService.saveOfferedService(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferedServiceResponse> getService(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(offeredServiceService.getOfferedService(id));
    }

    @GetMapping
    public ResponseEntity<List<OfferedServiceResponse>> getAllServices() {
        return ResponseEntity.status(HttpStatus.OK).body(offeredServiceService.getOfferedServices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferedServiceResponse> updateService(@PathVariable Long id, @Valid @RequestBody OfferedServiceRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(offeredServiceService.updateOfferedService(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        offeredServiceService.deleteOfferedService(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
