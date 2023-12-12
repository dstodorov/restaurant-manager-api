package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSectionResponse(
        Long id,
        String sectionName,
        Integer floor,
        Boolean active
) {
}
