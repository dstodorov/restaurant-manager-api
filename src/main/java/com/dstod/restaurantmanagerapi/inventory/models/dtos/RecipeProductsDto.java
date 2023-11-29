package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RecipeProductsDto(
        @Size.List(@Size(min = 1))
        List<RecipeProductDto> products
) {
}
