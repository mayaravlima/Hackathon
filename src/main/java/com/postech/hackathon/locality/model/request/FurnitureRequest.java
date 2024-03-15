package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Furniture;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FurnitureRequest(

        @NotNull(message = "Name cannot be null")
        String name,

        @Positive(message = "Quantity must be positive")
        int quantity
) {

        public Furniture toEntity() {
                return Furniture.builder()
                        .name(this.name())
                        .quantity(this.quantity())
                        .build();
        }
}
