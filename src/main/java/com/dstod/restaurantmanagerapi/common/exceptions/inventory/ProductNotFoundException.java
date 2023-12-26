package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_PRODUCT_NOT_FOUND;

public class ProductNotFoundException extends RMException {
    private List<Long> missingProducts = new ArrayList<>();
    public ProductNotFoundException(String message) {
        super(message, GLOBAL_EXCEPTION_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public ProductNotFoundException(String message, List<Long> missingProducts) {
        super(message, GLOBAL_EXCEPTION_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        this.missingProducts = missingProducts;
    }

    public List<Long> getMissingProducts() {
        return missingProducts;
    }
}
