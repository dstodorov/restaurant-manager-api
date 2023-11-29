package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

public class MismatchedObjectIdException extends RuntimeException{
    public MismatchedObjectIdException(String message) {
        super(message);
    }
}
