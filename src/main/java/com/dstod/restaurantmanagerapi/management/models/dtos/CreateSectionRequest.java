package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSectionRequest(
        @NotNull(message = "Section name must not be null")
        @NotBlank(message = "Section name must not be blank")
        String sectionName,
        @NotBlank(message = "Floor must not be blank")
        Integer floor
) {
}
