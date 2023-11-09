package com.dstod.restaurantmanagerapi.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "Supplier already exist.")
public class DuplicatedSupplierException extends RuntimeException {
    public DuplicatedSupplierException(String id) {
        super(id);
    }
}