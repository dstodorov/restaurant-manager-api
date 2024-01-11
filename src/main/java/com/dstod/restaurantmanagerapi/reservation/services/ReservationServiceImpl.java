package com.dstod.restaurantmanagerapi.reservation.services;

import com.dstod.restaurantmanagerapi.reservation.models.dtos.ReservationRequest;
import com.dstod.restaurantmanagerapi.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void newReservation(ReservationRequest request) {
        // check reservation person
        // check table/tables availability
        // change table status
        // change reservation status
        // generate reservation code
        // create reservation object
    }

    @Override
    public void confirmReservation() {

    }

    @Override
    public void modifyReservation() {

    }

    @Override
    public void cancelReservation() {

    }

    @Override
    public void generateReport() {

    }

    @Override
    public void addToWaitList() {

    }

    @Override
    public void tableAvailability() {

    }
}
