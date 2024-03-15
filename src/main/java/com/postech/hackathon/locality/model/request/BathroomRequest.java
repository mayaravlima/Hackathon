package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Bathroom;
import com.postech.hackathon.locality.entity.Facility;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BathroomRequest(

        @NotNull(message = "Name cannot be null")
        String type,

        @NotNull(message = "Facilities cannot be null")
        @Valid
        List<FacilityRequest> facilities

) {

    public Bathroom toEntity() {
        return Bathroom.builder()
                .type(this.type())
                .facilities(this.facilities().stream().map(FacilityRequest::toEntity).toList())
                .build();
    }
}
