package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_SUPPLIER_NOT_FOUND;

public class SupplierNotFoundException extends RMException {
    public SupplierNotFoundException(String message) {
        super(message, GLOBAL_EXCEPTION_SUPPLIER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
