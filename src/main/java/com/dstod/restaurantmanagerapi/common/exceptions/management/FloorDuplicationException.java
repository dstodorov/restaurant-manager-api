package com.dstod.restaurantmanagerapi.common.exceptions.management;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_FLOOR_DUPLICATION;

public class FloorDuplicationException extends RMException {
    public FloorDuplicationException(String message) {
        super(message, GLOBAL_EXCEPTION_FLOOR_DUPLICATION, HttpStatus.CONFLICT);
    }
}
