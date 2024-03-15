package com.postech.hackathon.locality.model.response;

import com.postech.hackathon.locality.entity.Bathroom;

import java.util.List;

public record BathroomResponse(
        Long id,
        String type,
        List<FacilityResponse> facilities

) {

    public static BathroomResponse fromEntity(Bathroom bathroom) {
        return new BathroomResponse(
                bathroom.getId(),
                bathroom.getType(),
                bathroom.getFacilities().stream().map(FacilityResponse::fromEntity).toList()
        );
    }
}
