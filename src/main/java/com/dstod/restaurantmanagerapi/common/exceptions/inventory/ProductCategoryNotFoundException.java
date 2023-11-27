package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

public class ProductCategoryNotFoundException extends RuntimeException{
    public ProductCategoryNotFoundException(String message) {
        super(message);
    }
}
