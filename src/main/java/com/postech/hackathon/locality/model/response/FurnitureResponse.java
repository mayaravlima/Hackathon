package com.postech.hackathon.locality.model.response;

import com.postech.hackathon.locality.entity.Furniture;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FurnitureResponse(
        Long id,
        String name,
        int quantity
) {

    public static FurnitureResponse fromEntity(Furniture furniture) {
        return new FurnitureResponse(
                furniture.getId(),
                furniture.getName(),
                furniture.getQuantity()
        );
    }
}
