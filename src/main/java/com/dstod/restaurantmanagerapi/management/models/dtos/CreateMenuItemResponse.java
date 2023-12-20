package com.dstod.restaurantmanagerapi.management.models.dtos;

import java.math.BigDecimal;

public record CreateMenuItemResponse(
        Long id,
        BigDecimal price,
        String additionalInformation,
        BaseMenuInfoDto menuInfo,
        ProductInfoDto product,
        RecipeInfoDto recipe
) {
}
