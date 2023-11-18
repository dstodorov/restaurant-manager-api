package com.dstod.restaurantmanagerapi.users.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserRequest(
        @Size(min = 2, max = 25)
        @NotNull
        String firstName,
        @Size(min = 2, max = 25)
        String middleName,
        @Size(min = 2, max = 25)
        @NotNull
        String lastName,
        @Size(min = 5, max = 20)
        @NotNull
        String username,
        @Size(min = 10)
        @NotNull
        String password,
        @Size(min = 1)
        List<String> roles,
        @Email
        String email,
        @Size(min = 6)
        @NotNull
        String phoneNumber
) {
}
