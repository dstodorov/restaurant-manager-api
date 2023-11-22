package com.dstod.restaurantmanagerapi.users.models.dtos;

import org.springframework.http.HttpStatus;

public record LoginError(HttpStatus httpStatus, String message) {
}
