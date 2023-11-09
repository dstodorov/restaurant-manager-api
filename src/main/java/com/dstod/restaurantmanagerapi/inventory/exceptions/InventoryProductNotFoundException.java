package com.dstod.restaurantmanagerapi.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Inventory product was not found.")
public class InventoryProductNotFoundException extends RuntimeException {
    public InventoryProductNotFoundException(String id) {
        super(id);
    }
}