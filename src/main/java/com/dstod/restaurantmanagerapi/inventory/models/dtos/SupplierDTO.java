package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SupplierDTO(
        Long id,
        @NotNull(message = "Missing vendor name")
        @Size(min = 2, max = 20)
        String name,
        @NotNull(message = "Missing vendor phone number")
        @Size(min = 10, max = 13)
        String phoneNumber,
        @Email
        @NotNull
        String email,
        String description,
        Boolean active
) {
}