package com.dstod.restaurantmanagerapi.reservation.services;

import com.dstod.restaurantmanagerapi.common.exceptions.reservation.InvalidReservationPersonTypeException;
import com.dstod.restaurantmanagerapi.reservation.models.dtos.ReservationRequest;
import com.dstod.restaurantmanagerapi.reservation.models.entities.Reservation;
import com.dstod.restaurantmanagerapi.reservation.models.enums.ReservationPersonType;
import com.dstod.restaurantmanagerapi.reservation.models.enums.ReservationStatus;
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

        if (!ReservationPersonType.isValidType(request.reservationPersonType())) {
            throw new InvalidReservationPersonTypeException(request.reservationPersonType());
        }
        Reservation reservation = new Reservation()
                .setClientName(request.clientName())
                .setClientPhoneNumber(request.clientPhoneNumber())
                .setDate(request.date())
                //TODO .setReservationPersonType();
                .setAdditionalInformation(request.additionalInformation())
                //TODO .setTables()
                .setReservationStatus(ReservationStatus.PENDING)
                //TODO .setReservationCode();
        ;
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
