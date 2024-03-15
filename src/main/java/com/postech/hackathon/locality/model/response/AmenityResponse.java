package com.postech.hackathon.locality.model.response;

import com.postech.hackathon.locality.entity.Amenity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AmenityResponse(
        Long id,
        String description,
        int quantity
) {

    public static AmenityResponse fromEntity(Amenity amenity) {
        return new AmenityResponse(
                amenity.getId(),
                amenity.getDescription(),
                amenity.getQuantity()
        );
    }
}
