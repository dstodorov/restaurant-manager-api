package com.dstod.restaurantmanagerapi.reservation.models.entities;

import com.dstod.restaurantmanagerapi.management.models.entities.RTable;
import com.dstod.restaurantmanagerapi.reservation.models.enums.ReservationPersonType;
import com.dstod.restaurantmanagerapi.reservation.models.enums.ReservationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_name", nullable = false)
    private String clientName;
    @Column(name = "client_phone_number", nullable = false)
    private String clientPhoneNumber;
    @Column(name = "additional_information")
    private String additionalInformation;
    @Column(nullable = false)
    private LocalDateTime date;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reservation_tables")
    private List<RTable> tables;
    @Column(name = "confirmation_date")
    private LocalDateTime confirmationDate;
    @Column(name = "reservation_code", unique = true, nullable = false)
    private String reservationCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_person_type")
    private ReservationPersonType reservationPersonType;
    @Column(name = "reservation_duration")
    private Integer reservationDuration;

    public Reservation() {
    }

    public Reservation(Long id, String clientName, String clientPhoneNumber, String additionalInformation, LocalDateTime date, List<RTable> tables, LocalDateTime confirmationDate, String reservationCode, ReservationStatus reservationStatus, ReservationPersonType reservationPersonType, Integer reservationDuration) {
        this.id = id;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.additionalInformation = additionalInformation;
        this.date = date;
        this.tables = tables;
        this.confirmationDate = confirmationDate;
        this.reservationCode = reservationCode;
        this.reservationStatus = reservationStatus;
        this.reservationPersonType = reservationPersonType;
        this.reservationDuration = reservationDuration;
    }

    public Long getId() {
        return id;
    }

    public Reservation setId(Long id) {
        this.id = id;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public Reservation setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public Reservation setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public Reservation setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }


    public List<RTable> getTables() {
        return tables;
    }

    public Reservation setTables(List<RTable> tables) {
        this.tables = tables;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Reservation setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public Reservation setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
        return this;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public Reservation setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
        return this;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public Reservation setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
        return this;
    }

    public ReservationPersonType getReservationPersonType() {
        return reservationPersonType;
    }

    public Reservation setReservationPersonType(ReservationPersonType reservationPersonType) {
        this.reservationPersonType = reservationPersonType;
        return this;
    }

    public Integer getReservationDuration() {
        return reservationDuration;
    }

    public Reservation setReservationDuration(Integer reservationDuration) {
        this.reservationDuration = reservationDuration;
        return this;
    }
}
