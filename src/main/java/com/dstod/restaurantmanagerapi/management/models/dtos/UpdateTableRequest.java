package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateTableRequest(
        @NotNull(message = "Section cannot be null")
        @NotBlank(message = "Section cannot be empty")
        @Size(min = 2, max = 25, message = "Section name must be between 2 and 25 symbols")
        String section,
        @Positive(message = "Capacity must be above zero")
        int capacity
) {
}
