package com.dstod.restaurantmanagerapi.common.exceptions.users;

public class UserRoleDoesNotExistException extends RuntimeException {
    public UserRoleDoesNotExistException(String message) {
        super(message);
    }
}
