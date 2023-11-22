package com.dstod.restaurantmanagerapi.users.models.dtos;

public record LoginResponse(
        String username,
        String token
) {
}
