package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found.")
public class ProductNotFoundException extends RuntimeException{
    private List<Long> missingProducts;
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, List<Long> missingProducts) {
        super(message);
        this.missingProducts = missingProducts;
    }

    public List<Long> getMissingProducts() {
        return missingProducts;
    }
}
