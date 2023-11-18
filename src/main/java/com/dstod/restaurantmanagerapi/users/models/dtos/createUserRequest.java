package com.dstod.restaurantmanagerapi.users.models.dtos;

import java.util.List;

public record createUserRequest (
        String firstName,
        String middleName,
        String lastName,
        String username,
        String password,
        List<String> roles,
        String email,
        String phoneNumber
){}
