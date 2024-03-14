package com.postech.hackathon.optionalService.controller;

import com.postech.hackathon.optionalService.model.OptionalItemRequest;
import com.postech.hackathon.optionalService.model.OptionalItemResponse;
import com.postech.hackathon.optionalService.service.OptionalItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/items")
@AllArgsConstructor
public class OptionalItemController {
    private final OptionalItemService optionalItemService;


    @PostMapping
    public ResponseEntity<OptionalItemResponse> createOptionItem(@Valid @RequestBody OptionalItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(optionalItemService.saveOptionalItem(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionalItemResponse> getOptionItems(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(optionalItemService.getOptionalItem(id));
    }

    @GetMapping()
    public ResponseEntity<List<OptionalItemResponse>> getAllOptionItems() {
        return ResponseEntity.status(HttpStatus.OK).body(optionalItemService.getOptionalItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OptionalItemResponse> updateOptionItem(@PathVariable Long id, @Valid @RequestBody OptionalItemRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(optionalItemService.updateOptionalItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOptionItem(@PathVariable Long id) {
        optionalItemService.deleteOptionalItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
