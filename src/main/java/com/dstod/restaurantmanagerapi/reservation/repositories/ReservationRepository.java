package com.dstod.restaurantmanagerapi.reservation.repositories;

import com.dstod.restaurantmanagerapi.reservation.models.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
