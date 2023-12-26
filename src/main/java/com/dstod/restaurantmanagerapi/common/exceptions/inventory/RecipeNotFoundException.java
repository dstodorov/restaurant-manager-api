package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_RECIPE_NOT_FOUND;

public class RecipeNotFoundException extends RMException {
    public RecipeNotFoundException(String message) {
        super(message, GLOBAL_EXCEPTION_RECIPE_NOT_FOUND, HttpStatus.CONFLICT);
    }
}
