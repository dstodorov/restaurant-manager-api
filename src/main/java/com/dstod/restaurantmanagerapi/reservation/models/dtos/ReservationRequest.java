package com.dstod.restaurantmanagerapi.reservation.models.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationRequest(
        String clientPhoneNumber,
        String clientName,
        LocalDateTime date,
        List<Long> tables,
        String additionalInformation,
        String reservationPersonType
) {
}
