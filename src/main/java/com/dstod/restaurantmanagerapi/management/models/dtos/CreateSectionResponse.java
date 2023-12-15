package com.dstod.restaurantmanagerapi.management.models.dtos;

public record CreateSectionResponse(
        Long id,
        String sectionName,
        Long floor,
        Boolean active
) {
}
