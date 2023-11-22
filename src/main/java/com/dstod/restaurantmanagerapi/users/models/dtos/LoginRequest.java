package com.dstod.restaurantmanagerapi.users.models.dtos;

public record LoginRequest(
        String username,
        String password
) {
}
