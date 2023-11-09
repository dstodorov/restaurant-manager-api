package com.dstod.restaurantmanagerapi.inventory.exceptions;

public class DuplicatedRecipeException extends RuntimeException {
    public DuplicatedRecipeException(String id) {
        super(id);
    }
}
