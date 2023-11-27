package com.dstod.restaurantmanagerapi.common.models;

import java.util.Date;
import java.util.List;

public record FailureResponse(
        String message,
        Date timestamp,
        List<String> errors
) {
}
