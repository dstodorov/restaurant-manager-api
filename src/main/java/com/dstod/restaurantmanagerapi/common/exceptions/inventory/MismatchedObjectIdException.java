package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_MISMATCH_ID;

public class MismatchedObjectIdException extends RMException {
    public MismatchedObjectIdException(String message) {
        super(message, GLOBAL_EXCEPTION_MISMATCH_ID, HttpStatus.CONFLICT);
    }
}
