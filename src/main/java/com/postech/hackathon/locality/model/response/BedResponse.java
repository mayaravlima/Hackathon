package com.postech.hackathon.locality.model.response;

import com.postech.hackathon.locality.entity.Bed;

public record BedResponse(
        Long id,
        String name,
        int quantity
) {

    public static BedResponse fromEntity(Bed bed) {
        return new BedResponse(
                bed.getId(),
                bed.getName(),
                bed.getQuantity()
        );
    }
}
