package com.dstod.restaurantmanagerapi.common.exceptions.management;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_MISSING_MENU_ITEM_DETAILS;

public class InvalidMenuItemInputException extends RMException {
    public InvalidMenuItemInputException(String message) {
        super(message, GLOBAL_EXCEPTION_MISSING_MENU_ITEM_DETAILS, HttpStatus.BAD_REQUEST);
    }
}
