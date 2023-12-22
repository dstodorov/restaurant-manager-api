package com.dstod.restaurantmanagerapi.management.models.dtos;

import java.util.List;

public record MenuItemCategoryDto(
        String category,
        List<MenuItemDto> items
) {
}
