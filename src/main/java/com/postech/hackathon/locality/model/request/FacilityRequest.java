package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Facility;
import jakarta.validation.constraints.NotNull;

public record FacilityRequest (

        @NotNull(message = "Name cannot be null")
        String name
) {

        public Facility toEntity() {
                return Facility.builder()
                        .name(this.name())
                        .build();
        }
}
