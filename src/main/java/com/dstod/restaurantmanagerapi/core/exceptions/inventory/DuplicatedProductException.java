package com.dstod.restaurantmanagerapi.core.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "Product already exist.")
public class DuplicatedProductException extends RuntimeException{
    public DuplicatedProductException(String message) {
        super(message);
    }
}
