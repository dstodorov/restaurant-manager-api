package com.dstod.restaurantmanagerapi.inventory.models.dtos;

public record CheckoutProductsRequestDTO(
        long recipeId,
        int quantity
) {

}
