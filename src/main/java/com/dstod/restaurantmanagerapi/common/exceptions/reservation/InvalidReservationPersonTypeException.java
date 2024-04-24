package com.dstod.restaurantmanagerapi.common.exceptions.reservation;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.INVALID_RESERVATION_PERSON_TYPE;

public class InvalidReservationPersonTypeException extends RMException {
    public InvalidReservationPersonTypeException(String errorMessage) {
        super(errorMessage, INVALID_RESERVATION_PERSON_TYPE, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
