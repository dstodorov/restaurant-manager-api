package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String id) {
        super(id);
    }
}
