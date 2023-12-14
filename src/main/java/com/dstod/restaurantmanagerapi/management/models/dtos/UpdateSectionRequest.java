package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.*;

public record UpdateSectionRequest(
        @NotNull(message = "Section name cannot be null")
        @NotBlank(message = "Section name cannot be blank")
        String sectionName,
        @Positive
        Integer floor,
        Boolean active
) {
}
