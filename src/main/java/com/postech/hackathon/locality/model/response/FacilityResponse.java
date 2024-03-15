package com.postech.hackathon.locality.model.response;

import com.postech.hackathon.locality.entity.Facility;

public record FacilityResponse(

        Long id,
        String name
) {

    public static FacilityResponse fromEntity(Facility facility) {
        return new FacilityResponse(
                facility.getId(),
                facility.getName()
        );
    }
}
