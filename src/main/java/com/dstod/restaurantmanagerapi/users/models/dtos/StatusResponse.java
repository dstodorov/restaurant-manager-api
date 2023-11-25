package com.dstod.restaurantmanagerapi.users.models.dtos;

import org.springframework.http.HttpStatus;

public record StatusResponse(
        HttpStatus httpStatus,
        String message
) {
}
