package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddInventoryProductDto(
        @Positive
        @NotNull
        Long productId,
        @Positive
        @NotNull
        Long supplierId,
        @Positive
        @NotNull
        Double orderQuantity,
        @Positive
        @NotNull
        BigDecimal batchPrice,
        @Future
        @NotNull
        LocalDate expiryDate
) {

}
