package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_DUPLICATED_RECIPE_PRODUCT;

public class RecipeProductDuplicationException extends RMException {
    public RecipeProductDuplicationException(String message) {
        super(message, GLOBAL_EXCEPTION_DUPLICATED_RECIPE_PRODUCT, HttpStatus.CONFLICT);
    }
}
