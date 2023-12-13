package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSectionRequest(
        @NotNull(message = "Section name cannot be null")
        @NotBlank(message = "Section name cannot be blank")
        String sectionName,
        @NotBlank(message = "Floor cannot be blank")
        Integer floor,
        @NotBlank(message = "Active status cannot be empty")
        Boolean active
) {
}
