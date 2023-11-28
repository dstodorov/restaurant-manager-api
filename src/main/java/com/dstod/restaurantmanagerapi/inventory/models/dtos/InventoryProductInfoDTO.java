package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InventoryProductInfoDTO(
        Long id,
        Double currentQuantity,
        SupplierInfoDTO supplier,
        ProductDto product,
        BigDecimal batchPrice,
        LocalDate expiryDate,
        Boolean wasted
) {
    public InventoryProductInfoDTO {

    }
}