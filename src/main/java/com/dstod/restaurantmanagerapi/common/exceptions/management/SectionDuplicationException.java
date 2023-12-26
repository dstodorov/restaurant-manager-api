package com.dstod.restaurantmanagerapi.common.exceptions.management;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_SECTION_DUPLICATION;

public class SectionDuplicationException extends RMException {
    public SectionDuplicationException(String message) {
        super(message, GLOBAL_EXCEPTION_SECTION_DUPLICATION, HttpStatus.CONFLICT);
    }
}
