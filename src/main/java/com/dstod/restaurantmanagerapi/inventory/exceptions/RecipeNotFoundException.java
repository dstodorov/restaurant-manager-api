package com.dstod.restaurantmanagerapi.inventory.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String id) {
        super(id);
    }
}
