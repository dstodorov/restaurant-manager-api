package com.dstod.restaurantmanagerapi.core.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Supplier was not found.")
public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(String id) {
        super(id);
    }
}
