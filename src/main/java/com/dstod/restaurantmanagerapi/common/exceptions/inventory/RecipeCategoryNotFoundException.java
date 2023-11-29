package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

public class RecipeCategoryNotFoundException extends RuntimeException{
    public RecipeCategoryNotFoundException(String message) {
        super(message);
    }
}
