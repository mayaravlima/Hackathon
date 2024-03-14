package com.postech.hackathon.optionalService.model;

import com.postech.hackathon.optionalService.entity.OptionalItem;

public record OptionalItemResponse(
        Long id,
        String name,
        double price
) {

    public static OptionalItemResponse fromEntity(OptionalItem entity) {
        return new OptionalItemResponse(entity.getId(), entity.getName(), entity.getPrice());
    }
}
