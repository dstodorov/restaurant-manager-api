package com.dstod.restaurantmanagerapi.common.exceptions.users;

public class UserDetailsDuplicationException extends RuntimeException {
    public UserDetailsDuplicationException(String message) {
        super(message);
    }
}
