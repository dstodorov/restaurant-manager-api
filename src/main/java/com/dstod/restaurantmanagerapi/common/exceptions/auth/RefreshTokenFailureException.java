package com.dstod.restaurantmanagerapi.common.exceptions.auth;

public class RefreshTokenFailureException extends RuntimeException{
    public RefreshTokenFailureException(String message) {
        super(message);
    }
}
