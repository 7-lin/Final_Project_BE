package com.fc.final7.domain.reservation.repository;

import com.fc.final7.domain.reservation.entity.ReservationOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationOptionRepository extends JpaRepository<ReservationOption, Long> {
}
