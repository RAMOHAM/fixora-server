package org.example.fixoraserver.booking;

import lombok.RequiredArgsConstructor;
import org.example.fixoraserver.booking.dto.BookingMapper;
import org.example.fixoraserver.booking.dto.BookingRequest;
import org.example.fixoraserver.booking.dto.BookingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Booking mappedBooking = bookingMapper.toEntity(bookingRequest);
        Booking savedBooking = bookingRepository.save(mappedBooking);
        return bookingMapper.toResponse(savedBooking);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::toResponse).toList();
    }
}
