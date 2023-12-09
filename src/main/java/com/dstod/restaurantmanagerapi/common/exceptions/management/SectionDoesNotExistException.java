package com.dstod.restaurantmanagerapi.common.exceptions.management;

public class SectionDoesNotExistException extends RuntimeException{
    public SectionDoesNotExistException(String message) {
        super(message);
    }
}
