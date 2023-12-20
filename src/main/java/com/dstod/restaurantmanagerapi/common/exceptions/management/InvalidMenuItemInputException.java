package com.dstod.restaurantmanagerapi.common.exceptions.management;

public class InvalidMenuItemInputException extends RuntimeException{
    public InvalidMenuItemInputException(String message) {
        super(message);
    }
}
