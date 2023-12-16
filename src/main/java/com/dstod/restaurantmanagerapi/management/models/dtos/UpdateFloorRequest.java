package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateFloorRequest(
        @Size(min = 2, message = "Floor name must be at least 2 characters long")
        @NotNull(message = "Floor name must be provided")
        @NotBlank(message = "Floor name cannot be black")
        String floorName
) {
}
