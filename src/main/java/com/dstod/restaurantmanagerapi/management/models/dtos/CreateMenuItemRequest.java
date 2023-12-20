package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateMenuItemRequest(
        @Positive(message = "Price must be positive number")
        @NotNull(message = "Price cannot be null")
        BigDecimal price,
        @NotNull(message = "Additional information is missing")
        @NotBlank(message = "Additional information is missing")
        String additionalInformation,
        @NotNull(message = "Menu Id cannot be null")
        @Positive(message = "Menu Id must be a positive number")
        Long menuId,
        Long recipeId,
        Long productId
) {
}
