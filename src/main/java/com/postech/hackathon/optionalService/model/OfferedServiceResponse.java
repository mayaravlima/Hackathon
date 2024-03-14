package com.postech.hackathon.optionalService.model;

import com.postech.hackathon.optionalService.entity.OfferedService;

public record OfferedServiceResponse(
        Long id,
        String name,
        double price
) {

    public static OfferedServiceResponse fromEntity(OfferedService entity) {
        return new OfferedServiceResponse(entity.getId(), entity.getName(), entity.getPrice());
    }
}
