package com.fc.final7.domain.reservation.repository;

import com.fc.final7.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "select r from Reservation r" +
            " join fetch r.periods rp" +
            " join fetch rp.productPeriod rppp" +
            " join fetch rppp.product rppppp" +
            " where r.member.id = :id")
    List<Reservation> selectReservations(@Param("id") Long id);

    @Query(value = "select r from Reservation r" +
            " join fetch r.periods rp" +
            " join fetch rp.productPeriod rpp" +
            " where r.id = :reservationId")
    Optional<Reservation> selectReservationDetailByReservationId(@Param("reservationId") Long reservationId);
}
