package org.example.fixoraserver.booking.repository;

import org.example.fixoraserver.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
