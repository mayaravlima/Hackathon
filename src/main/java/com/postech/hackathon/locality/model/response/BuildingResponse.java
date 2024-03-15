package com.postech.hackathon.locality.model.response;

import com.postech.hackathon.locality.entity.Building;

import java.util.List;

public record BuildingResponse(
        Long id,
        String name,
        List<RoomResponse> rooms
) {

    public static BuildingResponse fromEntity(Building building) {
        return new BuildingResponse(
                building.getId(),
                building.getName(),
                building.getRooms().stream().map(RoomResponse::fromEntity).toList()
        );
    }
}
