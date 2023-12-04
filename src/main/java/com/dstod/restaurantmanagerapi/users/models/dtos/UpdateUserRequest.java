package com.dstod.restaurantmanagerapi.users.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateUserRequest(
        @Size(min = 2, max = 25, message = "First name must be between 2 and 25 characters")
        @NotNull
        String firstName,
        @Size(min = 2, max = 25, message = "Middle name must be between 2 and 25 characters")
        String middleName,
        @Size(min = 2, max = 25, message = "Last name must be between 2 and 25 characters")
        @NotNull(message = "Last name must not be null")
        String lastName,
        @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
        @NotNull(message = "Username must not be null")
        String username,
        @Size(min = 10, message = "Password must have at least 10 characters")
        @NotNull(message = "Password must not be null")
        String password,
        @Size(min = 1, message = "You need at least on role")
        @NotNull(message = "You need at least on role")
        List<String> roles,
        @Email(message = "Email address is not valid")
        String email,
        @Size(min = 6, max = 20, message = "Phone number must be between 6 and 20 digits")
        @NotNull(message = "Phone number must not be null")
        String phoneNumber
) {

}
