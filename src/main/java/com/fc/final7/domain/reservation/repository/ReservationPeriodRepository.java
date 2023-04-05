package com.fc.final7.domain.reservation.repository;

import com.fc.final7.domain.reservation.entity.ReservationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationPeriodRepository extends JpaRepository<ReservationPeriod, Long> {
}
