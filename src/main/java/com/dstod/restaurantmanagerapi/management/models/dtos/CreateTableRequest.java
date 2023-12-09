package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateTableRequest(
        @NotNull(message = "Capacity should not be null")
        @PositiveOrZero(message = "Capacity should be more than zero")
        Integer capacity,
        @NotNull(message = "Section should not be null")
        @NotBlank(message = "Section should not be empty")
        String section
) {
}
