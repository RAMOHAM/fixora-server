package org.example.fixoraserver.booking;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fixoraserver.booking.dto.BookingRequest;
import org.example.fixoraserver.booking.dto.BookingResponse;
import org.example.fixoraserver.email.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        try{
            String bookingEmailTemplate = emailService.createEmailTemplate(bookingRequest);
            emailService.sendEmail(bookingEmailTemplate, newBooking.email());
        }catch (Exception e){
            log.error("Error sending email: {}", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    @GetMapping
    public ResponseEntity<@NonNull List<BookingResponse>> getAllBookings(){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookings());
    }
}
