package com.dstod.restaurantmanagerapi.inventory.utilities;

import com.dstod.restaurantmanagerapi.inventory.models.Product;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDTO;
import com.dstod.restaurantmanagerapi.inventory.models.enums.ProductCategory;
import com.dstod.restaurantmanagerapi.inventory.models.enums.UnitType;

public class InventoryUtility {
    public static ProductDTO createValidProductDto() {
        return new ProductDTO(
                1L,
                "Banana",
                "FRUIT",
                "KG"
        );
    }

    public static Product createValidProduct() {
        return new Product(
                1L,
                "Banana",
                ProductCategory.FRUIT,
                UnitType.KG
        );
    }

    public static ProductDTO createInvalidProductDto() {
        return new ProductDTO(
                1L,
                "Ban",
                "WRONG_CATEGORY",
                "WRONG_UNIT"
        );
    }
}
