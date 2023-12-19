package com.dstod.restaurantmanagerapi.management.models.dtos;

import java.util.Date;

public record BaseMenuInfoDto(
        Long id,
        String menuType,
        Double revision,
        Date lastUpdate,
        String updateComments
) {
}
