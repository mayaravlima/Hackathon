package com.postech.hackathon.optionalService.model;

import com.postech.hackathon.optionalService.entity.OfferedService;
import com.postech.hackathon.optionalService.entity.OptionalItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record OptionalItemRequest(
        @NotNull(message = "name is required")
        @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
        String name,

        @NotNull(message = "price is required")
        @Positive(message = "price must be greater than 0")
        double price
) {

        public OptionalItem toEntity() {
                return OptionalItem.builder()
                        .name(name())
                        .price(price())
                        .build();
        }

}
