package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "Supplier already exist.")
public class DuplicatedSupplierDetailsException extends RuntimeException {
    public DuplicatedSupplierDetailsException(String id) {
        super(id);
    }
}