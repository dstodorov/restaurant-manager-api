package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_DUPLICATED_PRODUCT;


public class DuplicatedProductException extends RMException {
    public DuplicatedProductException(String message) {
        super(message, GLOBAL_EXCEPTION_DUPLICATED_PRODUCT, HttpStatus.CONFLICT);
    }
}
