package com.dstod.restaurantmanagerapi.reservation.models.enums;

import com.dstod.restaurantmanagerapi.management.models.enums.MenuType;

import java.util.Arrays;

public enum ReservationPersonType {
    HOST, CLIENT;

    public static boolean isValidType(String reservationPersonType) {
        return Arrays
                .stream(ReservationPersonType.values())
                .anyMatch(type -> type.name().equalsIgnoreCase(reservationPersonType));
    }
}
