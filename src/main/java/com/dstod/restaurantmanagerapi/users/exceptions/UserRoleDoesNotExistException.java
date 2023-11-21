package com.dstod.restaurantmanagerapi.users.exceptions;

public class UserRoleDoesNotExistException extends RuntimeException {
    public UserRoleDoesNotExistException(String message) {
        super(message);
    }
}
