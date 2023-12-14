package com.dstod.restaurantmanagerapi.common.exceptions.management;

public class FloorDoesNotExistException extends RuntimeException {
    public FloorDoesNotExistException(String message) {
        super(message);
    }
}
