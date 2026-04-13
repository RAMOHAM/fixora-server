package org.example.fixoraserver.booking;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.fixoraserver.email.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final EmailService<Booking> emailService;

    @PostMapping
    public ResponseEntity<@NonNull Booking> createBooking(@RequestBody Booking booking) {
        Booking newBooking = bookingService.createBooking(booking);
        // email a client after booking is created
        String bookingEmailTemplate = emailService.createEmailTemplate(newBooking);
        emailService.sendEmail(bookingEmailTemplate, booking.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }
}
