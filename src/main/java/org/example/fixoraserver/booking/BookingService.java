package org.example.fixoraserver.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fixoraserver.booking.dto.BookingMapper;
import org.example.fixoraserver.booking.dto.BookingRequest;
import org.example.fixoraserver.booking.dto.BookingResponse;
import org.example.fixoraserver.email.EmailService;
import org.example.fixoraserver.professional.Professional;
import org.example.fixoraserver.professional.ProfessionalService;
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
    private final EmailService<BookingRequest> emailService;
    private final ProfessionalService professionalService;

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

    public BookingResponse updateBookingStatus(String bookingId, BookingStatus status) {
        BookingResponse updatedBooking = changeBookingStatus(bookingId, status);
        String bookingEmailTemplate = emailService.createEmailTemplate(toEmailRequest(updatedBooking));
        emailService.sendEmail(bookingEmailTemplate, updatedBooking.email());
        return updatedBooking;
    }

    public BookingResponse assignProfessionalToBooking(String bookingId, String professionalId){
        Professional assignedProfessional = professionalService.getProfessionalById(professionalId);
        Booking updatedBooking = bookingRepository.findById(parseBookingId(bookingId)).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        updatedBooking.setProfessional(assignedProfessional);
        return bookingMapper.toResponse(bookingRepository.save(updatedBooking));
    }

    public BookingRequest toEmailRequest(BookingResponse booking) {
        return new BookingRequest(
                booking.id(),
                booking.jobDescription(),
                booking.email(),
                booking.address(),
                booking.phone(),
                booking.dateOfJob(),
                booking.preferredWindow(),
                booking.category(),
                booking.bookingStatus().name(),
                booking.videoInput(),
                booking.professionalId()
        );
    }
}
