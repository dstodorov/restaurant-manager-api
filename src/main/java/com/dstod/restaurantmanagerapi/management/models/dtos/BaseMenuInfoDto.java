package com.dstod.restaurantmanagerapi.management.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public record BaseMenuInfoDto(
        Long id,
        String menuType,
        Double revision,
        Date lastUpdate,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String updateComments
) {
}
