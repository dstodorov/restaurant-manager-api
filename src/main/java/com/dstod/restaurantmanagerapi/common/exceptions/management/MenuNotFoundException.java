package com.dstod.restaurantmanagerapi.common.exceptions.management;

public class MenuNotFoundException extends RuntimeException{
    public MenuNotFoundException(String message) {
        super(message);
    }
}
