package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Amenity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AmenityRequest(
        @NotNull(message = "description is required")
        String description,

        @Positive(message = "quantity must be greater than 0")
        int quantity
) {

    public Amenity toEntity() {
        return Amenity.builder()
                .description(this.description())
                .quantity(this.quantity())
                .build();
    }
}
