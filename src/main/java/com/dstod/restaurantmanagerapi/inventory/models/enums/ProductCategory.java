package com.dstod.restaurantmanagerapi.inventory.models.enums;

import java.util.Arrays;

public enum ProductCategory {
    MEAT, SEAFOOD, VEGETABLE, FRUIT, GRAINS, DIARY, NUT_SEED, HERB_SPICE, ALCOHOLIC_DRINK, NON_ALCOHOLIC_DRINK;

    public static boolean isValidCategory(String categoryName) {
        return Arrays.stream(ProductCategory.values()).anyMatch(p -> p.name().equalsIgnoreCase(categoryName));
    }
}
