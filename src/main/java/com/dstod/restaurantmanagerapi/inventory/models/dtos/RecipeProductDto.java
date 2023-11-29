package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import jakarta.validation.constraints.Positive;

public record RecipeProductDto(
        Long productId,
        @Positive
        Double quantity
) {
}