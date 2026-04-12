package org.example.fixoraserver.booking.repository;

import lombok.NonNull;
import org.example.fixoraserver.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<@NonNull Booking,@NonNull Long> {
}
