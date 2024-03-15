package com.postech.hackathon.locality.model.response;

import com.postech.hackathon.locality.entity.Locality;

import java.util.List;

public record LocalityResponse(
        Long id,
        String name,
        AddressResponse address,
        List<AmenityResponse> amenities,
        List<BuildingResponse> buildings

) {

    public static LocalityResponse fromEntity(Locality locality) {
        return new LocalityResponse(
                locality.getId(),
                locality.getName(),
                AddressResponse.fromEntity(locality.getAddress()),
                locality.getAmenities().stream().map(AmenityResponse::fromEntity).toList(),
                locality.getBuildings().stream().map(BuildingResponse::fromEntity).toList()
        );
    }
}
