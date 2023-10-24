package com.dstod.restaurantmanagerapi.core.exceptions.inventory;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String id) {
        super(id);
    }
}
