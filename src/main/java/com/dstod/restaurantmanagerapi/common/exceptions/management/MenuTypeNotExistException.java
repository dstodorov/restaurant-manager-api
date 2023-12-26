package com.dstod.restaurantmanagerapi.common.exceptions.management;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_MENU_DETAILS_ERROR;

public class MenuTypeNotExistException extends RMException {
    public MenuTypeNotExistException(String message) {
        super(message, GLOBAL_EXCEPTION_MENU_DETAILS_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
