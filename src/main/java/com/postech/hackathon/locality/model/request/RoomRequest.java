package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Room;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record RoomRequest(

        @NotNull(message = "Name cannot be null")
        String type,

        @Positive(message = "Capacity must be positive")
        int capacity,

        @NotNull(message = "Furnitures cannot be null")
        @Valid
        List<FurnitureRequest> furnitures,

        @NotNull(message = "Beds cannot be null")
        @Valid
        List<BedRequest> beds,

        @NotNull(message = "Bathrooms cannot be null")
        @Valid
        BathroomRequest bathroom,

        @Positive(message = "Price must be positive")
        double price,

        @Positive(message = "Quantity must be positive")
        int quantity,

        boolean active
) {
        public Room toEntity() {
                return Room.builder()
                        .type(this.type())
                        .capacity(this.capacity())
                        .furnitures(this.furnitures().stream().map(FurnitureRequest::toEntity).toList())
                        .beds(this.beds().stream().map(BedRequest::toEntity).toList())
                        .bathroom(this.bathroom().toEntity())
                        .price(this.price())
                        .quantity(this.quantity())
                        .active(this.active())
                        .build();
        }
}
