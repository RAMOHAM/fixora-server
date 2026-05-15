package org.example.fixoraserver.booking.dto;

public record BookingRequest (
        String id,
        String jobDescription,
        String email,
        String address,
        String phone,
        String dateOfJob,
        String preferredWindow,
        String category,
        String bookingStatus,
        String videoInput
){}
