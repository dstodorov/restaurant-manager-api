package com.dstod.restaurantmanagerapi.inventory.models.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RecipeDTO(
        Long id,
        @Size(min = 3, max = 40)
        @NotNull
        String name,
        @Pattern(regexp = "^APPETIZER$|^SALAD$|^MAIN$|^DESSERT$|^COCKTAIL$|^HOT_DRINK$|^COLD_DRINK$")
        @NotNull
        String category
) {
}
