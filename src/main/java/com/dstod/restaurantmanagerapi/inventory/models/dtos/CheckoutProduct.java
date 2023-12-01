package com.dstod.restaurantmanagerapi.inventory.models.dtos;

public record CheckoutProduct(
        String productName,
        double quantityCheckedOut
) {
}
