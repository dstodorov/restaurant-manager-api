package com.dstod.restaurantmanagerapi.management.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public record CreateMenuItemResponse(
        Long id,
        BigDecimal price,
        String additionalInformation,
        BaseMenuInfoDto menu,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        ProductInfoDto product,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        RecipeInfoDto recipe
) {
}
