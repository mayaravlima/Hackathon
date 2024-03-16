package com.postech.hackathon.locality.model.response;

import com.postech.hackathon.locality.entity.Room;

import java.util.List;
import java.util.Objects;

public record RoomResponse(
        Long id,
        String type,
        int capacity,

        List<FurnitureResponse> furnitures,

        List<BedResponse> beds,

        BathroomResponse bathroom,

        double price,

        int quantity,

        boolean active
) {

    public static RoomResponse fromEntity(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getType(),
                room.getCapacity(),
                room.getFurnitures().stream().map(FurnitureResponse::fromEntity).toList(),
                room.getBeds().stream().map(BedResponse::fromEntity).toList(),
                Objects.nonNull(room.getBathroom()) ? BathroomResponse.fromEntity(room.getBathroom()) : null,
                room.getPrice(),
                room.getQuantity(),
                room.isActive()
        );
    }
}
