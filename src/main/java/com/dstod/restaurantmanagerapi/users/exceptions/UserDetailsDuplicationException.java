package com.dstod.restaurantmanagerapi.users.exceptions;

public class UserDetailsDuplicationException extends RuntimeException {
    public UserDetailsDuplicationException(String message) {
        super(message);
    }
}
