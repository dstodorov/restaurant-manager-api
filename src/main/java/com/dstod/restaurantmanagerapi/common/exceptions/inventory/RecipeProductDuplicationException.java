package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

public class RecipeProductDuplicationException extends RuntimeException {
    public RecipeProductDuplicationException(String message) {
        super(message);
    }
}
