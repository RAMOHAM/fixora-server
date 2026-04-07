package org.example.fixoraserver.booking.service;

import lombok.RequiredArgsConstructor;
import org.example.fixoraserver.booking.model.Booking;
import org.example.fixoraserver.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
}
