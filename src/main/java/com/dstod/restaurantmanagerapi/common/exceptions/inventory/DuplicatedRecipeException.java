package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

public class DuplicatedRecipeException extends RuntimeException {
    public DuplicatedRecipeException(String id) {
        super(id);
    }
}
