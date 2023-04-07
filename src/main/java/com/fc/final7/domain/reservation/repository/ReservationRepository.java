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
            " join fetch rp.productPeriod rpp" +
            " join fetch rpp.product rppp" +
            " where r.member.id = :id")
    List<Reservation> selectReservations(@Param("id") Long id);

    @Query(value = "select r from Reservation r" +
            " join fetch r.periods rp" +
            " join fetch rp.productPeriod rpp" +
            " join fetch rpp.product rppp" +
            " where r.id = :reservationId")
    Optional<Reservation> selectReservationDetailByReservationId(@Param("reservationId") Long reservationId);

    @Query(value = "select r from Reservation r" +
            " join fetch r.periods rp" +
            " join fetch rp.productPeriod rpp" +
            " join fetch rpp.product rppp" +
            " where r.reservationCode = :reservationCode")
    Optional<Reservation> findReservationByReservationCode(@Param("reservationCode") String reservationCode);
}
