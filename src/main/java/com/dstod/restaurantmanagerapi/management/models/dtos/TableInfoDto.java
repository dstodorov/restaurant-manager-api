package com.dstod.restaurantmanagerapi.management.models.dtos;

public record TableInfoDto(
        long id,
        long tableNumber,
        String sectionName,
        int capacity
) {
}
