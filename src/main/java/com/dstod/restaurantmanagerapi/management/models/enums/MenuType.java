package com.dstod.restaurantmanagerapi.management.models.enums;

import com.dstod.restaurantmanagerapi.inventory.models.enums.RecipeCategory;

import java.util.Arrays;

public enum MenuType {
    MAIN, DRINKS;

    public static boolean isValidMenuType(String menuType) {
        return Arrays
                .stream(MenuType.values())
                .anyMatch(type -> type.name().equalsIgnoreCase(menuType));

    }
}
