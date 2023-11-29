package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RecipeDto(
        Long id,
        @Size(min = 3, max = 40)
        @NotNull
        String name,
        @Size(min = 30)
        @NotNull
        String preparationMethod,
        @NotNull
        String category
) {
}
