package com.dstod.restaurantmanagerapi.management.models.dtos;

import java.util.List;

public record MenuDto(
    List<MenuItemCategoryDto> menu
) {
}
