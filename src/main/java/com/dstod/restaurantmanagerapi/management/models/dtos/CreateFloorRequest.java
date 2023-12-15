package com.dstod.restaurantmanagerapi.management.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CreateFloorRequest(
        @Size(min = 2)
        String floorName
) {
}
