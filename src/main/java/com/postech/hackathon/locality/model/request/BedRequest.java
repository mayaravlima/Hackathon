package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Bed;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BedRequest(

        @NotNull(message = "Name cannot be null")
        String name,

        @Positive(message = "Quantity must be positive")
        int quantity
) {

        public Bed toEntity() {
                return Bed.builder()
                        .name(this.name())
                        .quantity(this.quantity())
                        .build();
        }
}
