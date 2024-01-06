package com.dstod.restaurantmanagerapi.reservation.services;

import com.dstod.restaurantmanagerapi.reservation.repositories.ReservationRepository;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}
