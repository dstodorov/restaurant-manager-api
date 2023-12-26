package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_PRODUCT_DETAILS_ERROR;

public class ProductUnitNotFoundException extends RMException {
    public ProductUnitNotFoundException(String message) {
        super(message, GLOBAL_EXCEPTION_PRODUCT_DETAILS_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
