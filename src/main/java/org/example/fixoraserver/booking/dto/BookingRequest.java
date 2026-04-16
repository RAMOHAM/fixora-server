package org.example.fixoraserver.booking.dto;

import org.example.fixoraserver.booking.BookingStatus;

public record BookingRequest (
        String jobDescription,
        String email,
        String address,
        String phone,
        String dateOfJob,
        String preferredWindow,
        String category,
        BookingStatus bookingStatus
){}
