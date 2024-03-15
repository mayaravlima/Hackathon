package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Locality;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LocalityRequest (

        @NotNull(message = "Name cannot be null")
        String name,

        @NotNull(message = "Address cannot be null")
        @Valid
        AddressRequest address,

        @NotNull(message = "Amenities cannot be null")
        @Valid
        List<AmenityRequest> amenities,

        @NotNull(message = "Buildings cannot be null")
        @Valid
        List<BuildingRequest> buildings
) {

        public Locality toEntity() {
                return Locality.builder()
                        .name(this.name())
                        .address(this.address().toEntity())
                        .amenities(this.amenities().stream().map(AmenityRequest::toEntity).toList())
                        .buildings(this.buildings().stream().map(BuildingRequest::toEntity).toList())
                        .build();
        }
}
