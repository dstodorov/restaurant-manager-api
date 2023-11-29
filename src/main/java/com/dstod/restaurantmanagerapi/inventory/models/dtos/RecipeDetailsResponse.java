package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import java.util.List;

public record RecipeDetailsResponse(
        RecipeDto recipe,
        List<RecipeProductInfoResponse> products
) {
}
