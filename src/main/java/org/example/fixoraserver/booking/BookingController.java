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
        String bookingEmailTemplate = emailService.createEmailTemplate(bookingService.toEmailRequest(newBooking));
        emailService.sendEmail(bookingEmailTemplate, newBooking.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    @GetMapping
    public ResponseEntity<@NonNull List<BookingResponse>> getAllBookings(){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookings());
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<@NonNull BookingResponse> confirmBookingById(@PathVariable String id, @RequestParam String professionalId) {
        bookingService.updateBookingStatus(id, BookingStatus.CONFIRMED);
        BookingResponse updatedBooking = bookingService.assignProfessionalToBooking(id, professionalId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBooking);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<@NonNull BookingResponse> cancelBookingById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.updateBookingStatus(id, BookingStatus.CANCELLED));
    }
}
