package com.dstod.restaurantmanagerapi.common.exceptions.management;

public class MenuTypeNotExistException extends RuntimeException{
    public MenuTypeNotExistException(String message) {
        super(message);
    }
}
