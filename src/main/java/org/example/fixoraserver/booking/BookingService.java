package org.example.fixoraserver.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fixoraserver.booking.dto.BookingMapper;
import org.example.fixoraserver.booking.dto.BookingRequest;
import org.example.fixoraserver.booking.dto.BookingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Booking mappedBooking = bookingMapper.toEntity(bookingRequest);
        Booking savedBooking = bookingRepository.save(mappedBooking);
        log.info("Booking created: {}", savedBooking.getId());
        return bookingMapper.toResponse(savedBooking);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::toResponse).toList();
    }

    public BookingResponse changeBookingStatus(String bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(parseBookingId(bookingId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        booking.setBookingStatus(status);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    private Long parseBookingId(String bookingId) {
        try {
            return Long.valueOf(bookingId);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid booking id");
        }
    }
}
