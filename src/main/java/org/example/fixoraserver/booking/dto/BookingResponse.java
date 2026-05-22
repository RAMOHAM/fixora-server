package org.example.fixoraserver.booking.dto;

import org.example.fixoraserver.booking.BookingStatus;

public record BookingResponse (
        String id,
        String jobDescription,
        String email,
        String address,
        String phone,
        String dateOfJob,
        String preferredWindow,
        String category,
        String videoInput,
        BookingStatus bookingStatus,
        String professionalId
){}
