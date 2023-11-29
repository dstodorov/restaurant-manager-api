package com.dstod.restaurantmanagerapi.inventory.models.enums;

import java.util.Arrays;

public enum RecipeCategory {
    APPETIZER, SALAD, MAIN, DESSERT, COCKTAIL, HOT_DRINK, COLD_DRINK;

    public static boolean isValidCategory(String categoryType) {
        return Arrays
                .stream(RecipeCategory.values())
                .anyMatch(category -> category.name().equalsIgnoreCase(categoryType));

    }
}
