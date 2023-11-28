package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDto(Long id,
                         @NotNull
                         @Size(min = 2)
                         String name,
                         @NotNull
                         String category,
                         @NotNull
                         String unit) {
}