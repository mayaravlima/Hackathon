package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Building;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BuildingRequest (

        @NotNull(message = "Name cannot be null")
        String name,

        @Valid
        @NotNull(message = "Rooms cannot be null")
        List<RoomRequest> rooms
) {

            public Building toEntity() {
                    return Building.builder()
                            .name(this.name())
                            .rooms(this.rooms().stream().map(RoomRequest::toEntity).toList())
                            .build();
            }
}
