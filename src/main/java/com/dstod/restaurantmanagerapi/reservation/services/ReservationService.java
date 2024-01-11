package com.dstod.restaurantmanagerapi.reservation.services;

import com.dstod.restaurantmanagerapi.reservation.models.dtos.ReservationRequest;
import com.dstod.restaurantmanagerapi.reservation.repositories.ReservationRepository;

public interface ReservationService {

    void newReservation(ReservationRequest request);

    void confirmReservation();

    void modifyReservation();

    void cancelReservation();

    void generateReport();

    void addToWaitList();

    void tableAvailability();
}
