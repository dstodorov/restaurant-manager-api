package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_RECIPE_DETAILS_ERROR;

public class RecipeCategoryNotFoundException extends RMException {
    public RecipeCategoryNotFoundException(String message) {
        super(message, GLOBAL_EXCEPTION_RECIPE_DETAILS_ERROR, HttpStatus.CONFLICT);
    }
}
