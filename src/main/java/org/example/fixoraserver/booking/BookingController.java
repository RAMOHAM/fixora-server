package org.example.fixoraserver.booking;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.fixoraserver.booking.dto.BookingRequest;
import org.example.fixoraserver.booking.dto.BookingResponse;
import org.example.fixoraserver.email.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final EmailService<BookingRequest> emailService;

    @PostMapping
    public ResponseEntity<@NonNull BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {
        BookingResponse newBooking = bookingService.createBooking(bookingRequest);
        // email a client after booking is created
        String bookingEmailTemplate = emailService.createEmailTemplate(bookingRequest);
        emailService.sendEmail(bookingEmailTemplate, newBooking.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    @GetMapping
    public ResponseEntity<@NonNull List<BookingResponse>> getAllBookings(){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookings());
    }

    @PutMapping("/confirmation")
    public ResponseEntity<@NonNull BookingResponse> confirmBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.changeBookingStatus(bookingRequest.id(), BookingStatus.CONFIRMED));
    }

    @PutMapping("/cancellation")
    public ResponseEntity<@NonNull BookingResponse> cancelBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.changeBookingStatus(bookingRequest.id(), BookingStatus.CANCELLED));
    }
}
