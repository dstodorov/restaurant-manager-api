package com.dstod.restaurantmanagerapi.common.models;

import java.util.Date;

public record SuccessResponse(
        String message,
        Date timestamp,
        Object savedObject
) {
}
