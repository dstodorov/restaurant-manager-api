package com.dstod.restaurantmanagerapi.common.exceptions.management;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_MISSING_FLOOR;

public class FloorDoesNotExistException extends RMException {
    public FloorDoesNotExistException(String message) {
        super(message, GLOBAL_EXCEPTION_MISSING_FLOOR, HttpStatus.NOT_FOUND);
    }
}
