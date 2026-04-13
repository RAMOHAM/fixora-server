package org.example.fixoraserver.booking;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<@NonNull Booking,@NonNull Long> {
}
