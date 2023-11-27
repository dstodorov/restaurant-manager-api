package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

public class ProductUnitNotFoundException extends RuntimeException{
    public ProductUnitNotFoundException(String message) {
        super(message);
    }
}
