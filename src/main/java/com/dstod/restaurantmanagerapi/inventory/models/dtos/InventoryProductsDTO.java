package com.dstod.restaurantmanagerapi.inventory.models.dtos;

import java.util.List;

public record InventoryProductsDTO (
        List<InventoryProductInfoDTO> inventoryProducts
) {

}
