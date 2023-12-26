package com.dstod.restaurantmanagerapi.common.exceptions.management;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_MISSING_SECTION;

public class SectionDoesNotExistException extends RMException {
    public SectionDoesNotExistException(String message) {
        super(message, GLOBAL_EXCEPTION_MISSING_SECTION, HttpStatus.NOT_FOUND);
    }
}
