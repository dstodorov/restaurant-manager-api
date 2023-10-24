package com.dstod.restaurantmanagerapi.core.exceptions.inventory;

public class DuplicatedRecipeException extends RuntimeException {
    public DuplicatedRecipeException(String id) {
        super(id);
    }
}
