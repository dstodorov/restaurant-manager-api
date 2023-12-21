package com.dstod.restaurantmanagerapi.management.models.dtos;

import java.math.BigDecimal;

public record MenuItemDto(
    String name,
    BigDecimal price,
    String additionalInformation
) {
}
